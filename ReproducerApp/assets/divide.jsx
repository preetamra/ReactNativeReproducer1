import Svg, {G,Path,Rect,Circle,Line} from 'react-native-svg';
import {StyleSheet,View,Text} from 'react-native';

export default function devide(props) {
 // console.log("devide :- ",props);
    return(
        <View style={styles.container}>
            {/* <Svg 
              xmlns="http://www.w3.org/2000/svg" 
              width="100" 
              height="100" 
              viewBox="0 0 100 100">
                <Circle 
                  cx="50" 
                  cy="30" 
                  r="5" 
                  fill={props.value && "green"}/>
                <Line 
                  x1="30" 
                  y1="50" 
                  x2="70" 
                  y2="50" 
                  stroke="black" 
                  stroke-width="3"/>
                <Circle 
                  cx="50" 
                  cy="70" 
                  r="5" 
                  fill={!props.value && "red"}/>
          </Svg> */}
          <Text
          style={{
            color:!props.value ? "red" : "green",
            fontSize:24,
            fontWeight:"700"
          }}
          >
             {!props.value && "OFF"}
             {props.value && "ON"}            
          </Text>
        </View>  
    )
}



const styles = StyleSheet.create({
    container: {
      justifyContent:"center",
      alignItems:"center",
    },
  });