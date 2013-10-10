
package nz.geek.findlay.skeleton;

import org.acra.annotation.ReportsCrashes;

@ReportsCrashes(formKey = "YOUR_FORM_KEY")
public class Application extends android.app.Application
{

    private static Application instance;

    @Override
    public void onCreate() {
        //ACRA.init(this);
        super.onCreate();
        instance = this;
    }

    public static Application getInstance() {
        return instance;
    }

    public static String getLogTag(Class clazz) {
        return instance.getApplicationInfo().name + "_" + clazz.getSimpleName();
    }

}
