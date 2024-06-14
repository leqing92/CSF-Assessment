// IMPORTANT: Do not modify this file

// Code from https://medium.com/@impulsejs/convert-dataurl-to-a-file-in-javascript-1921b8c3f4b
export const dataToImage = (dataUrl: string, filename = 'pic.png') => {
  var arr = dataUrl.split(",")
  // @ts-ignore
  const mime = arr[0].match(/:(.*?);/)[1]
  const  bstr = atob(arr[arr.length - 1])
  var n = bstr.length
  // @ts-ignore
  const u8arr = new Uint8Array(n);
  while (n--) {
    u8arr[n] = bstr.charCodeAt(n);
  }
  return new File([u8arr], filename, { type: mime });
}

export const dataURItoBlob = (dataURI: String, filename = 'pic.png') => {
  let mimeString = dataURI.split(',')[0].split(';')[0];  //extract content type
  // console.info("contentType", mimeString);
  var byteString = atob(dataURI.split(',')[1]); 
  var ab = new ArrayBuffer(byteString.length)
  var ia = new Uint8Array(ab)

  // populate the btyestring into blob(btye[])
  for(var i = 0; i < byteString.length; i++){
    ia[i] = byteString.charCodeAt(i);
  }

  return new Blob([ab], {type: mimeString});
}

