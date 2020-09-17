// Information to reach API
const url =document.location.href;
const queryParams ='search/';

// Select elements
let inputField = document.getElementById('bookSearch');
let header = document.getElementById('bookList');
    //at first hide the list card
    header.style.display="none";
 //searched book will be listed here
let nextHeader= document.getElementById('where_the_searched_book_listed');

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

//검색어 API에 들어가 있는 <br>를 없애는 함수
function removeTag(String){
    let orgText = String;
    let newText = orgText.replace(/<(\/b|b)([^>]*)>/gi,"");
    return newText
}

function populateHeader(jsonObj){
    let origin = document.getElementById('bookList')

    for(let i=0; i<jsonObj.length; i++){

        let card = document.cloneNode(origin)
        let bookName = jsonObj[i]['title'];
        console.log(bookName);
        let bookWriter = jsonObj[i]['author'];
        console.log(bookWriter);
        let bookDetails = jsonObj[i]['description'];
        console.log(bookDetails);
        let bookImg = jsonObj[i]['image'];
        console.log(bookImg);

        document.getElementById('bookName').innerHTML = removeTag(bookName);
        document.getElementById('author').innerHTML = "작가: "+removeTag(bookWriter)+"<br>상세정보: "+removeTag(bookDetails);
        //document.getElementsByClassName('mdl-card__title')[0].style.backgroundImage ="url("+bookImg+")";
        document.getElementById('card-img').src = bookImg

        nextHeader.append(card);
    }

}

/*
function mykeydown(){
  if(event.keyCode == 13){
   getBookInfo();
   header.style.display ="block";

  }
}
*/

function myKeyFunc(){
   getBookInfo();
   if(inputField.value.length>1){
       header.style.display ="block";
   }else
        header.style.display="none";



}

console.clear();






