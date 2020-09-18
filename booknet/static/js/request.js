// Information to reach API
const url =document.location.href;
const queryParams ='search/';
const feedParams ='feed/';

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

    /*nextHeader.style.display='none';*/
    $(document).ready();
    let booklist = $('#where_the_searched_book_listed')
    booklist.animate({opacity:0},0);

    for(let i=0; i<jsonObj.length; i++){

        let bookName = jsonObj[i]['title'];
        console.log(bookName);
        let bookWriter = jsonObj[i]['author'];
        let bookDetails = jsonObj[i]['description'];
        let bookImg = jsonObj[i]['image'];
        let bookIsbn = jsonObj[i]['isbn'].split(' ')[1];

        document.getElementById('bookName').innerHTML = removeTag(bookName);
        document.getElementById('author').innerHTML = "작가: "+removeTag(bookWriter);
        document.getElementById('details').innerHTML = "상세정보: "+removeTag(bookDetails);
        document.getElementById('card-img').src = bookImg;
        console.log(url+feedParams+bookIsbn);
        let flink = url+feedParams+bookIsbn;
        document.getElementById('feedLink').setAttribute('href',flink);

        let card = header.cloneNode(true);
        nextHeader.append(card);

        console.log(document.getElementById('feedButton'));

    }
    header.style.display="none";
    booklist.animate({opacity:1, queue:false}, 500);
}

function myKeyFunc(){
   getBookInfo();
   if(inputField.value.length>=1){
       header.style.display ="block";
   }else
        header.style.display="none";

}
function myRefresh(){

    location.href = url+feedParams;
}

console.clear();

function myFeedFunc(string){
    console.log(string);
   location.href = url+feedParams+string;


}






