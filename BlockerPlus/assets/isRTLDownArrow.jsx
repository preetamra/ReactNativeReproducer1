import Svg, {G,Path,Rect} from 'react-native-svg';
import {StyleSheet,View} from 'react-native';

export default function IsRTLDownArrow(props) {
    return(
        <View style={styles.container}>
            <Svg 
            xmlns="http://www.w3.org/2000/svg" 
            width="20px" 
            height="20px" 
            viewBox="0 0 1024 1024" 
            class="icon" 
            version="1.1">
                <Path 
                d="M768 903.232l-50.432 56.768L256 512l461.568-448 50.432 56.768L364.928 512z" fill="#000000"/>
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