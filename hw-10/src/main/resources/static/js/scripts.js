function getBookById() {
    const id = getIdFromUrl();

    if (id) {
        fetch('/api/book/' + id)
            .then(response => response.json())
            .then(book => {
                console.log(book)
                document.getElementById('book-title').append(book.title);
                document.getElementById('book-author').append(getAuthors(book));
                document.getElementById('book-genre').append(getGenres(book));
                document.getElementById('book-comments').append(getComments(book));
            });
    }
}

let getIdFromUrl = function getUrlParameter() {
    let sPageURL = window.location.href;
    let sURLVariables = sPageURL.split('/');
    let bookIdx = sURLVariables.findIndex(id => id === ('book'));
    let idIdx = sURLVariables[bookIdx + 1];
    console.log(sURLVariables[bookIdx]);
    console.log(idIdx);
    return idIdx;
};

getAuthors = val => {
    let str = '';
    val.authors.forEach(author => {
        str += author.name + '\n';
    })
    return str;
}

getGenres = val => {
    let str = '';
    val.genres.forEach(genre => {
        str += genre.name + '\n';
    })
    return str;
}

getComments = val => {
    let fragment = new DocumentFragment();
    val.comments.forEach(com => {
        let li = document.createElement('li');
        li.append(com.text);
        fragment.append(li);
    })
    return fragment;
}

