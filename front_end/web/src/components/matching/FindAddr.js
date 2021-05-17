import DaumPostcode from 'react-daum-postcode';

const handleComplete = (data) => {
  let fullAddress = data.address;
  let extraAddress = "";

  if (data.addressType === "R") {
    if (data.bname !== "") {
      extraAddress += data.bname;
    }
    if (data.buildingName !== "") {
      extraAddress +=
        extraAddress !== "" ? `, ${data.buildingName}` : data.buildingName;
    }
    fullAddress += extraAddress !== "" ? ` (${extraAddress})` : "";
  }
  console.log(extraAddress)
};

const FindAddr = () => {

    const postCodeStyle = {
      display: "block",
      position: "absolute",
      top: "50px",
      zIndex: "100",
      padding: "7px"
    }
    
	return (
    	<DaumPostcode 
          onComplete={handleComplete}
          style={postCodeStyle}
          height={700}
        />
    )
}

export default FindAddr;