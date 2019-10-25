################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
CPP_SRCS += \
../src-tests/StoreTest.cpp \
../src-tests/TestException.cpp 

OBJS += \
./src-tests/StoreTest.o \
./src-tests/TestException.o 

CPP_DEPS += \
./src-tests/StoreTest.d \
./src-tests/TestException.d 


# Each subdirectory must supply rules for building sources it contributes
src-tests/%.o: ../src-tests/%.cpp
	@echo 'Building file: $<'
	@echo 'Invoking: GCC C++ Compiler'
	g++ -I"/home/fabrizio/Workspaces/workspaceISSRE/Store_V2/src" -O0 -g3 -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


