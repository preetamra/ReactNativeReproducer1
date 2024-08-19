import Svg, {G,Path,Rect} from 'react-native-svg';
import {StyleSheet,View} from 'react-native';

export default function LeftArrow(props) {
    return(
        <View style={styles.container}>
            <Svg 
            xmlns="http://www.w3.org/2000/svg" 
            width="40px" 
            height="40px">
                <G 
                id="_4-Arrow_Left" 
                data-name="4-Arrow Left">
                    <Path 
                    d="M32,15H3.41l8.29-8.29L10.29,5.29l-10,10a1,1,0,0,0,0,1.41l10,10,1.41-1.41L3.41,17H32Z"
                    fill="#000000"
                    />
                </G>
            </Svg>
        </View>  
    )
}

const styles = StyleSheet.create({
    container: {
      justifyContent:"flex-start",
      alignContent:"center",
      marginLeft:15       
    } 
}
);
