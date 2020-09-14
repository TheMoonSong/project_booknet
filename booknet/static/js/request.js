// Information to reach API
const url =document.location.href;
const queryParams ='search/';

// Select elements
let inputField = document.getElementById('bookSearch');
let header = document.getElementById('bookList');
    //at first hide the list card
    header.style.display="none";
let nextHeader= document.getElementById('h1');




// AJAX function
const getBookInfo = () => {
  console.log(inputField.value);
  let wordQuery = inputField.value;
  const endpoint = url+queryParams+wordQuery;
  //XML
  const xhr = new XMLHttpRequest();
  xhr.responseType ='json';

  xhr.onreadystatechange=()=>{
    if (xhr.readyState === XMLHttpRequest.DONE) {
      console.log(xhr.response);
    }
  }
  console.log(endpoint);
  xhr.open('GET',endpoint);
  xhr.send();

  //load bookInfo
  xhr.onload = () =>{
    let bookInfo = xhr.response;

    populateHeader(bookInfo['items']);

  }
}
function removeTag(String){
    let orgText = String;
    let newText = orgText.replace(/<(\/b|b)([^>]*)>/gi,"");
    return newText
}

function populateHeader(jsonObj){

  for(let i=0;i<jsonObj.length;i++){

       let card = header.cloneNode(true);

       let bookName = jsonObj[i]['title'];
       let bookWriter = jsonObj[i]['author'];
       let bookPrice = jsonObj[i]['price'];
       let bookDetails = jsonObj[i]['description'];
       let bookImg = jsonObj[i]['image'];
       console.log(bookImg);

       document.getElementById('bookName').innerHTML = removeTag(bookName);
       document.getElementById('bookDetails').innerHTML = removeTag(bookDetails);
       document.getElementsByClassName('mdl-card__title')[0].style.backgroundImage ="url("+bookImg+")";

       header.appendChild(card);



  }







}





function mykeydown(){
  if(event.keyCode == 13){
   getBookInfo();
   header.style.display ="block";

  }
}

console.clear();

angular.module('MyApp').controller('AppCtrl', function($scope) {});




