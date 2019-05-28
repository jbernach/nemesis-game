using System;
using MonoTouch.Foundation;
using MonoTouch.UIKit;

using playn.ios;
using playn.core;
using com.superjose128.nemesis.core;

namespace com.superjose128.nemesis
{
  [Register ("AppDelegate")]
  public partial class AppDelegate : IOSApplicationDelegate {
    public override bool FinishedLaunching (UIApplication app, NSDictionary options) {
      app.SetStatusBarHidden(true, true);
      var pconfig = new IOSPlatform.Config();
      // use pconfig to customize iOS platform, if needed
      IOSPlatform.register(app, pconfig);
      PlayN.run(new NemesisGame());
      return true;
    }
  }

  public class Application {
    static void Main (string[] args) {
      UIApplication.Main (args, null, "AppDelegate");
    }
  }
}
