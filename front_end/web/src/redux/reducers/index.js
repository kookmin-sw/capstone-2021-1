import { REGISTER_USER, LOGIN_USER, DOUBLECHECKED_ID } from "../types/index";
import axios from 'axios';

const USER_URL = "http://54.180.98.138:8080";

function registerUser(dataToSubmit) {
  console.log(dataToSubmit)
  const data = axios({
    method: 'post',
    url: USER_URL + "/signup",
    data:{
      uid: dataToSubmit.uid,
      password: dataToSubmit.password,
      nickname: dataToSubmit.nickname,
      name: dataToSubmit.name,
      phoneNumber: dataToSubmit.phoneNumber,
      provider: dataToSubmit.provider,
      address: dataToSubmit.address
    },
})
return {
  type: DOUBLECHECKED_ID,
  payload: data,
};
}

function doubleChecked_ID(dataToSubmit){
  const data = axios({
    method:'put',
    url: USER_URL + "/member/validate",
    data:{
      uid: dataToSubmit.uid
    }
  })

  return {
    type: DOUBLECHECKED_ID,
    payload: data,
  };
}

function loginUser(dataToSubmit) {
  const data = axios({
    method:'post',
    url: USER_URL+"/signin",
    data: {
      uid: dataToSubmit.uid,
      password : dataToSubmit.password,
      nickname: dataToSubmit.nickname,
      name: dataToSubmit.name,
      phoneNumber:dataToSubmit.phoneNumber,
    }
  })
  return {
    type: LOGIN_USER,
    payload: data,
  };
}

const InitialState = { isLogin: false };

function reducer(state = InitialState, action){
    switch (action.type) {
        case REGISTER_USER:
            return state = {
                ...state,
            }
        case LOGIN_USER:
            return {
                ...state,
                login_data: action.payload,
                isLogin: true
            }
        case DOUBLECHECKED_ID:
          return {
            ...state,
            double_ckecked_id : action.payload,
          }
        default: 
            return state;
    }
}

const actionCreators = {
    loginUser,
    registerUser,
    doubleChecked_ID
  };

export { actionCreators };

export default reducer;



