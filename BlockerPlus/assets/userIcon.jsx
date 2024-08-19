import Svg, { Circle, Rect,G,Path} from 'react-native-svg';
import {StyleSheet,View} from 'react-native';

export default function UserIcon(props) {
    return(
        <View>
            <Svg fill="#000000" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" id="person"><G data-name="Layer 2"><Path d="M12 11a4 4 0 1 0-4-4 4 4 0 0 0 4 4zm6 10a1 1 0 0 0 1-1 7 7 0 0 0-14 0 1 1 0 0 0 1 1z" data-name="person"/></G></Svg>
        </View>  
    )
}

const styles = StyleSheet.create({
    container: {
    transform:[{rotate:"90deg"}],
    },
  });