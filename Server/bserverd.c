#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <string.h>
#include "ServerTest.h"
//#include "MyJava_Testa.h"
#include <string.h>
#include <signal.h>
FILE *fp= NULL;
JNIEnv *env;
JavaVM *jvm;
JNIEnv* create_vm(JavaVM ** jvm) {
	JNIEnv *env;
	JavaVMInitArgs vm_args;
	JavaVMOption options;
	options.optionString = "-Djava.class.path=/home/boom/TestServer"; //Path to the java source code
	vm_args.version = JNI_VERSION_1_6; //JDK version. This indicates version 1.6
	vm_args.nOptions = 1;
	vm_args.options = &options;
	vm_args.ignoreUnrecognized = 0;
	int ret = JNI_CreateJavaVM(jvm, (void**)&env, &vm_args);
	if(ret < 0)
		printf("\nUnable to Launch JVM\n");   	
	return env;
}

void signal_handler(int arg)
{
	if(arg==SIGTERM){
		fclose(fp);
		(*jvm)->DestroyJavaVM(jvm);
		exit(0);
	}
}

int main(void)
{
	pid_t process_id = 0;
	pid_t sid = 0;
	process_id = fork();
	if (process_id < 0){
		printf("fork failed!\n");
		exit(1);
	}
	if (process_id > 0){
		printf("process_id of child process %d \n", process_id);
		exit(0);
	}
	sid = setsid();
	if(sid < 0)
	{
		exit(1);
	}
	umask(0);

	// Change the current working directory to root.
	chdir("/");
	// Close stdin. stdout and stderr
	close(STDIN_FILENO);
	close(STDOUT_FILENO);
	close(STDERR_FILENO);
	
	signal(SIGTERM, &signal_handler);  
	
	// Open a log file in write mode.
	fp = fopen ("/home/boom/TestServer/log.txt", "w+");
	jmethodID mainMethod = NULL;
	env = create_vm(&jvm);
	jclass clsJava=NULL;
	if (env == NULL){
		fprintf(fp,"\n Unable to create environment");
		return 1;
	}
	clsJava = (*env)->FindClass(env,"ServerTest");
	if (clsJava != NULL){
		fprintf(fp,"\n Able to find the requested class\n");  
	}
	else{
		fprintf(fp,"\n Unable to find the requested class\n");    	
	}
	mainMethod = (*env)->GetStaticMethodID(env, clsJava, "main", "([Ljava/lang/String;)V");
	if (mainMethod != NULL){
		fprintf(fp,"\n Calling the Java Main method");
		(*env)->CallStaticVoidMethod(env, clsJava, mainMethod, NULL);
	}
	while(1){
		sleep(10);
	}
	return 0;
}
