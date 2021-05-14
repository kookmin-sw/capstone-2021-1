import { REGISTER_USER, LOGIN_USER, DOUBLECHECKED_ID, SET_USER_DETAIL, SET_HEADER ,SET_USER_CREW, SET_CLICK_MARKER, SET_CREW_DATA, SET_MATCHING_DATA, POST_MATCHING, USER_REQUEST_MATCHING} from "../types/index";
import axios from 'axios';

const USER_URL = "http://54.180.98.138:8080";
function registerUser(dataToSubmit) {
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
}).then(function(response){
  alert("회원 가입 되었습니다.");

}).catch(function(error){
  alert(error.message);
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
      uid: dataToSubmit
    }
  }).then(function(response){
    if (response.data == false){
      alert("사용 가능한 ID 입니다.")
    }else{
      alert("이미 있는 ID 입니다.")
    }
  }).catch(function(error){
    alert(error.message)
  })
  return {
    type: DOUBLECHECKED_ID,
    payload: data,
  };
}

function setCrewData(data){
  return{
    type: SET_CREW_DATA,
    payload: data
  }
}

function setMatchingData(data){
  return{
    type: SET_MATCHING_DATA,
    payload: data
  }
}

function setUserCrew(data){
  return{
    type: SET_USER_CREW,
    payload: data
  }
}

function setUserDetail(data){
  return{
    type: SET_USER_DETAIL,
    payload: data
  }
}

function userRequestMatching(dataToSubmit, token){
  const data = axios({
    method:'post',
    url: USER_URL + "/matching/participate/" + dataToSubmit,
    headers: {'X-AUTH-TOKEN': token}
  }).then(function(response){
    alert("매칭에 요청 했습니다.")
  }).catch(function(error){
    alert(error.message)
  }) 
  return{
    type: USER_REQUEST_MATCHING,
    payload:data
  }
}
function postMatching(dataToSubmit, token){
  const data = axios({
    method:'post',
    url: USER_URL + "/matching",
    headers: {'X-AUTH-TOKEN': token},
    data:{
        "title":dataToSubmit.title,
        "description":dataToSubmit.description,
        "latitude":  dataToSubmit.latitude,
        "longitude": dataToSubmit.longitude,
        "maxCount":dataToSubmit.maxCount,
        "category":dataToSubmit.category,
        "startTime":"2021-09-20T12:00:00"
    }
  }).then(function(response){
    alert("매칭을 생성 했습니다.")
  }).catch(function(error){
    alert(error.message)
  })
  return{
    type: POST_MATCHING,
    payload:data
  }
}
function setHeader(data){
  return {
    type : SET_HEADER,
    payload : data
  }
}

function setClickMarker(data){
  return{
    type: SET_CLICK_MARKER,
    payload:data
  }
}

function loginUser(data) {
  return {
    type : LOGIN_USER,
    payload : data,
  }
}

const InitialState = { isLogin: false };

function reducer(state = InitialState, action){
    switch (action.type) {
        case USER_REQUEST_MATCHING:
          return{
            ...state,
          }
        case POST_MATCHING:
          return{
            ...state,
          }
        case SET_MATCHING_DATA:
          return{
            ...state,
            matchingDatas: action.payload,
          }
        case SET_CREW_DATA:
          return{
            ...state,
            crewDatas: action.payload,
          }
        case SET_CLICK_MARKER:
          return{
            ...state,
            ClickMarker: action.payload,
          }
        case SET_USER_DETAIL:
          return{
            ...state,
            userDetail: action.payload,
          }
        case SET_USER_CREW:
          return{
            ...state,
            userCrew: action.payload,
          }
        case SET_HEADER:
          return{
            ...state,
            request_header : action.payload
          }
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
    doubleChecked_ID,
    setHeader,
    setUserDetail,
    setUserCrew,
    setClickMarker,
    setCrewData,
    setMatchingData,
    postMatching,
    userRequestMatching
  };

export { actionCreators };

export default reducer;



