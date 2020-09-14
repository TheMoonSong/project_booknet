// Information to reach API
const url ="http://127.0.0.1:8000/";
const queryParams ='search/';

// Select elements
let inputField = document.getElementById('bookSearch');
let header = document.getElementById('bookList');
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
    showBookList(bookInfo['items']);

  }
}

function populateHeader(jsonObj){
   let bookHeader = document.createElement('h2');
   let bookAuthor = document.createElement('p');
   let bookPrice = document.createElement('p');
    let bookDetail = document.createElement('p');

  for(let i=0;i<jsonObj.length;i++){
       console.log(jsonObj[i]);

            bookHeader.textContent = '책이름: '+jsonObj[i]['title'];
            header.appendChild(bookHeader);
            bookAuthor.textContent ='작가:'+jsonObj[i]['author'];
            header.appendChild(bookAuthor);
            bookPrice.textContent = '가격: '+jsonObj[i]['price'];
            header.appendChild(bookPrice);
            bookDetail.textContent = '상세정보: '+jsonObj[i]['description'];
            header.appendChild(bookDetail);


  }







}
function showBookList(jsonObj){
  let book
}




function mykeydown(){
  if(event.keyCode == 13){
   getBookInfo();
  }
}




