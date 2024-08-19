import {Mixpanel} from 'mixpanel-react-native';
import {token as MixpanelToken, trackAutomaticEvents} from './app.json';
import DeviceInfo from 'react-native-device-info';

export class MixpanelManager {
    static sharedInstance = MixpanelManager.sharedInstance || new MixpanelManager();

    constructor() {
        this.mixpanel = new Mixpanel(MixpanelToken, trackAutomaticEvents);
        this.mixpanel.init();
        (
            async() => {
               try{
                let data = await DeviceInfo.getAndroidId();
                  this.mixpanel.identify(data)
               }catch(err) {
                console.log(err);
                this.mixpanel.track("error Assigning UserId");
               }
            }
        )()
        this.mixpanel.setLoggingEnabled(true);
    }
}

export const MixpanelInstance = MixpanelManager.sharedInstance.mixpanel;