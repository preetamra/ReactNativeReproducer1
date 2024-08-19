import Svg, {G,Path,Rect} from 'react-native-svg';
import {StyleSheet,View} from 'react-native';

export default function DownArrow(props) {
    return(
        <View style={styles.container}>
            <Svg width="20px" height="20px" viewBox="0 0 1024 1024" class="icon"  version="1.1" xmlns="http://www.w3.org/2000/svg">
                <Path d="M903.232 256l56.768 50.432L512 768 64 306.432 120.768 256 512 659.072z" fill="#000000" />
            </Svg>
        </View>  
    )
}

const styles = StyleSheet.create({
    container: {
        alignItems:"center"       
    } 
}
);