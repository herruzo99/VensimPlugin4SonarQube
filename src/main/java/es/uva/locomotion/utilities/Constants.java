package es.uva.locomotion.utilities;

import java.util.Arrays;
import java.util.List;

public class Constants {

    private Constants(){
        throw new IllegalStateException("Utility class");
    }
    public static final List<String> FUNCTIONS_THAT_FORM_COMPOUND_MAGIC_NUMBERS = Arrays.asList("SQRT","TAN","TANH","SIN","SINH","COS",
            "COSH","ARCTAN", "ARCSIN","ARCCOS","ABS","LN","GAMMA LN","INTEGER","GAME","EXP","POWER","LOG","MODULO","QUANTUM");


    public static final List<String> IMPURE_FUNCTIONS = Arrays.asList("INTEG","STEP","DELAY1", "DELAY1I", "DELAY3", "DELAY3I",
            "FORECAST", "SMOOTH3", "SMOOTH3I", "SMOOTHI", "SMOOTH", "TREND","RAMP","RANDOM 0 1","RANDOM BETA","RANDOM BINOMIAL",
            "RANDOM EXPONENTIAL", "RANDOM GAMMA", "RANDOM LOOKUP","RANDOM NEGATIVE BINOMIAL","RANDOM NORMAL", "RANDOM PINK NOISE",
            "RANDOM POISSON","RANDOM TRIANGULAR","RANDOM UNIFORM","RANDOM WEIBULL");

    // This symbols are defined by default in every Vensim file
    public static final List<String> DEFAULT_VENSIM_SYMBOLS= Arrays.asList("TIME", "FINAL TIME", "INITIAL TIME", "SAVEPER", "TIME STEP");

    public static  final List<String> IGNORED_MAGIC_NUMBERS = Arrays.asList("0","1","100","-1");

    public static final String DICTIONARY_SERVICE_PARAMETER = "vensim.dictionary.service";
    public static final String DICTIONARY_USERNAME_PARAMETER =  "vensim.dictionary.username";
    public static final String DICTIONARY_CREDENTIAL_PARAMETER = "vensim.dictionary.password";
    public static final String DICTIONARY_LOG_SERVER_COMMUNICATIONS = "vensim.log.serverMessages";
    public static final String LOG_IN_FILE = "vensim.log.file";
    public static final String VIEW_PREFIX = "vensim.view.prefix";
    public static final String MODULE_NAME = "vensim.view.module.name";
    public static final String MODULE_SEPARATOR = "vensim.view.module.separator";
    public static final String CATEGORY_SEPARATOR = "vensim.view.category.separator";
    public static final String CREATE_GET_DIFF_FILE = "vensim.dictionary.getDiff";
    public static final String AUXILIARY_FILES_DIR_NAME = "vensim.auxiliaryFiles.directoryName";
    public static final String INJECT = "vensim.dictionary.inject";
}
