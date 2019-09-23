package org.sonar.vensim;

import org.sonar.api.Plugin;

public class VensimPlugin implements Plugin {


    @Override
    public void define(Context context) {


        context.addExtensions(VensimLanguage.class,
                VensimQualityProfile.class,
                VensimSquidSensor.class,
                VensimRuleRepository.class
        );

    }

}
