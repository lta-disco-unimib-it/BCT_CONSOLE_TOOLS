. ./bdci.config.sh

rm -r AnalysisV*
test=test



dateStartGDB=$(date +"%s")

echo PATH:$PATH


java -Dbct.pinHome=$PIN_HOME -Dbct.generatePINprobes=true -Dbct.monitorGlobalVariables=true -Dbct.useEntryPointDetector=false -cp $CLASSPATH '-Dbct.functionsToFilterOut=check_object;handle_commit;filter_refs;parse_long_opt;strbuf_readlink;strbuf_getline;strbuf_expand;strbuf_vaddf;strbuf_add_commented_lines;strbuf_split_buf;strbuf_addstr_urlencode;strbuf_expand_dict_cb;strbuf_read;strbuf_add;strbuf_read_file;strbuf_addchars.constprop.2;read_sha1_strbuf;strbuf_utf8_replace;strbuf_add_wrapped_text;strbuf_wrap;strbuf_addstr_xml_quoted;strbuf_list_free;strbuf_fread;strbuf_splice;strbuf_commented_addf;strbuf_getwholeline;strbuf_getwholeline_fd;strbuf_addbuf_percentquote;strbuf_adddup;strbuf_add;die;die_errno;xrealloc;parse_object;xmalloc;prepare_alt_odb;setup_git_env;get_parent' it.unimib.disco.lta.bdci.SetupBDCI $EsempioV0 $EsempioV0/$test $EsempioV1 $EsempioV1/$test $EsempioV2/ $EsempioV2/$test

pushd AnalysisV0/BCT/BCT_DATA/BCT/conf/files/scripts/originalSoftware.gdb.config.txt.probes/
bash compile.sh
popd

pushd AnalysisV1/BCT/BCT_DATA/BCT/conf/files/scripts/originalSoftware.gdb.config.txt.probes/
bash compile.sh
popd

pushd AnalysisV2/BCT/BCT_DATA/BCT/conf/files/scripts/originalSoftware.gdb.config.txt.probes/
bash compile.sh
popd

