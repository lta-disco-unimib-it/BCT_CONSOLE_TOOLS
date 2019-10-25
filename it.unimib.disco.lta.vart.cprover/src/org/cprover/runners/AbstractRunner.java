package org.cprover.runners;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import org.cprover.communication.Message;
import org.cprover.communication.Result;
import org.cprover.communication.Tag;
import org.cprover.launch.VerifyConfig;
import org.cprover.launch.VerifyConfig.Verifier;
import org.eclipse.debug.core.ILaunch;

public abstract class AbstractRunner {
    
    protected VerifyConfig config;

    protected ILaunch launch;

    public LinkedList<Message> messages;

    public Result result;

    public long timeStamp;

    public int logPos;

    public AbstractRunner(VerifyConfig config, ILaunch launch) {
        this.config = config;
        this.launch = launch;
        this.messages = new LinkedList<Message>();
        this.result = Result.UNKNOWN;
        this.timeStamp = 0;
        this.logPos = 0;
    }

    public String getVerifierExecutable() {
        return config.verifier.getName();
    }

    protected LinkedList<String> packOptions() {
        LinkedList<String> opts = new LinkedList<String>();
        if (config.verifier != Verifier.EBMC && config.verifier != Verifier.VCEGAR) {
            if (!config.mainFun.equals("")) {
                opts.add("--function");
                opts.add(config.mainFun);
            }
            if (config.endianess.equals("big_endian"))
                opts.add("--big-endian");
            if (config.endianess.equals("little_endian"))
                opts.add("--little-endian");
            if (config.word_width.equals("_LP64"))
                opts.add("--LP64");
            if (config.word_width.equals("_ILP64"))
                opts.add("--ILP64");
            if (config.word_width.equals("_LLP64"))
                opts.add("--LLP64");
            if (config.word_width.equals("_ILP32"))
                opts.add("--ILP32");
            if (config.word_width.equals("_LP32"))
                opts.add("--LP32");
            if (config.chk_array_bounds)
                opts.add("--bounds-check");
            if (!config.chk_assertions)
                opts.add("--no-assertions");
            if (config.chk_div_by_zero)
                opts.add("--div-by-zero-check");
            if (config.chk_overflow)
                opts.add("--overflow-check");
            if (config.chk_pointers)
                opts.add("--pointer-check");
            if (config.nan_check)
                opts.add("--nan-check");
            
            if (config.round_mode.equals("round_to_zero"))
                opts.add("--round-to-zero");
            else if (config.round_mode.equals("round_to_minus_inf"))
                opts.add("--round-to-minus-inf");
            else if (config.round_mode.equals("round_to_plus_inf"))
                opts.add("--round-to-plus-inf");
            else if (config.round_mode.equals("round_to_nearest"))
                opts.add("--round-to-nearest");
        }
        if (config.verifier == Verifier.CBMC || config.verifier == Verifier.HW_CBMC) {
            if (!config.cbmc_assertions)
                opts.add("--no-unwinding-assertions");
            if (config.cbmc_beautify.equals("greedy"))
                opts.add("--beautify-greedy");
            else if (config.cbmc_beautify.equals("pbs"))
                opts.add("--beautify-pbs");
                        
            if (config.cbmc_all_claims)
                opts.add("--all-claims");
            //if (!config.cbmc_simplify)
            //    opts.add("--no-simplify");
            if (config.cbmc_slice)
                opts.add("--slice");
            //if (!config.cbmc_substitution)
            //    opts.add("--no-substitution");
        }
        if (config.verifier == Verifier.HW_CBMC || config.verifier == Verifier.EBMC || config.verifier == Verifier.VCEGAR) {
            if (!config.hwcbmc_module.equals("")) {
                opts.add("--module");
                opts.add(config.hwcbmc_module);
            }
        }
        if (config.verifier == Verifier.SATABS) {
            if (!config.satabs_abstractor.equals("default")) {
                opts.add("--abstractor");
                opts.add(config.satabs_abstractor);
            }
            if (!config.satabs_modelchecker.equals("default")) {
                opts.add("--modelchecker");
                opts.add(config.satabs_modelchecker);
            }
            if (!config.satabs_refiner.equals("default")) {
                opts.add("--refiner");
                opts.add(config.satabs_refiner);
            }
            if (config.satabs_modelchecker.equals("boppo") && config.satabs_loop_detection) {
                opts.add("--loop-detection");
            }
        }
        return opts;
    }

    protected Tag parseTag(String line) {
        int p0 = line.indexOf('<');
        int p1 = line.indexOf('>');
        int p2 = line.indexOf("</");
        if (p0 == -1 || p1 == -1 || p2 == p0)
            return null;
        
        int P1_SIZE = 1;
        int _p1 = line.indexOf("/>");
        if (  _p1 > 0  ){
        	p1=_p1;
        	P1_SIZE=2;
        }
        
        Tag t = new Tag();
        
        String tagContent = line.substring(p0+1, p1);
        
        String[] values = tagContent.split(" ");
        for ( String value : values ){
        	
        	if ( ! value.contains("=") ){
        		t.name=value;
        		continue;
        	}
        	
        	
        	System.out.println("VALUE:"+value);
        	String[] splitted = value.split("=");
        	String tagName = splitted[0];
        	
        	
        	
        	String tagValue = splitted[1];
        	tagValue = tagValue.substring(1,tagValue.length()-1);
        	
        	if ( tagName.equals("type") ){
        		t.type=tagValue;
//        	} else if ( tagName.equals("name") ){
//        		t.name=tagValue;
        	} else {
        		t.setAttribute( tagName, tagValue );
        	}
        }
        
//        int pt = line.indexOf(" type=");
//        if (pt > p0 && pt < p1) {
//            t.name = line.substring(p0 + 1, pt);
//            t.type = line.substring(pt + 7, p1 - 1);
//            if (t.type.equals("ERROR"))
//                this.result = Result.ERROR;
//        } else {
//            int pn = line.indexOf(" name=");
//            if (pn == -1) {
//                t.name = line.substring(p0 + 1, p1);
//                t.type = "";
//            } else {
//                t.name = line.substring(p0 + 1, pn);
//                t.type = line.substring(pn + 7, p1 - 1);
//            }
//        }
        
//        String content[] = line.substring(p0+1, p1).split(" ");
//        if ( content.length == 0 ){
//        	return null;
//        }
        
//        t.name = content[0];
        if (p2 != -1)
            t.value = line.substring(p1 + P1_SIZE, p2);
        else
            t.value = "";
        t.value = t.value.replaceAll("&amp;", "&");
        t.value = t.value.replaceAll("&lt;", "<");
        t.value = t.value.replaceAll("&gt;", ">");
        t.value = t.value.replaceAll("&quot;", "\"");
        t.value = t.value.replaceAll("&#10;", "\n");
        t.value = t.value.replaceAll("&#13;", "\r");
        t.value = t.value.replaceAll("&#9;", "\t");
        return t;
    }

    protected String convertStreamToString(InputStream is) throws IOException {
        /*
         * To convert the InputStream to String we use the BufferedReader.readLine() method. We iterate until the BufferedReader return null which means there's
         * no more data to read. Each line will appended to a StringBuilder and returned as String.
         */
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line + "\n");
        }
        return sb.toString();
    }

    protected List<String> convertStreamToList(InputStream is) throws IOException {
        /*
         * To convert the InputStream to String we use the BufferedReader.readLine() method. We iterate until the BufferedReader return null which means there's
         * no more data to read. Each line will appended to a StringBuilder and returned as String.
         */
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        LinkedList<String> list = new LinkedList<String>();
        String line = null;
        while ((line = reader.readLine()) != null) {
            list.add(line);
        }
        return list;
    }

    protected List<String> convertStringToList(String s) {
        List<String> list = new LinkedList<String>();
        for (StringTokenizer tok = new StringTokenizer(s, "\n"); tok.hasMoreTokens();) {
            String line = tok.nextToken();
            list.add(line);
        }
        return list;
    }

    /**
     * @return the config
     */
    public VerifyConfig getConfig() {
        return config;
    }
}
