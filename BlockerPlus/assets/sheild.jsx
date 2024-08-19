import Svg, { Circle, Rect,G,Path} from 'react-native-svg';
import {StyleSheet,View} from 'react-native';

export default function sheild(props) {
    return(
        <View>
            <Svg xmlns="http://www.w3.org/2000/svg" fill={props.isEnabled?"#00FF00":"#FF0000"} version="1.1" id="Layer_1" x="0px" y="0px" width="26px" height="26px" viewBox="0 0 105.802 122.88" enable-background="new 0 0 105.802 122.88"><G><Path fill-rule="evenodd" clip-rule="evenodd" d="M105.689,0H0.112v60.381c-1.824,26.659,18.714,50.316,52.789,62.499 c34.074-12.183,54.613-35.84,52.789-62.499V0L105.689,0z M52.869,8.61H9.417v48.832c-0.022,0.313-0.039,0.625-0.053,0.938h43.504 v49.557l0.142,0.052c27.313-9.715,43.927-28.454,42.962-49.646H52.869V8.61L52.869,8.61z"/></G></Svg>
        </View>  
    )
}

const styles = StyleSheet.create({
    container: {
    transform:[{rotate:"90deg"}],
    },
  });