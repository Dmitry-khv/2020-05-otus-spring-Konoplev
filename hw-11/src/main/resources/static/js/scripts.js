function getBookById() {
    const id = getIdFromUrl();

    if (id) {
        fetch('/api/book/' + id + '/view')
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
    return sURLVariables[bookIdx + 1];
};

getAuthors = val => {
    let str = '';
    val.authors.forEach(author => {
        str += author.name + '\n';
    });
    return str;
}

getGenres = val => {
    let str = '';
    val.genres.forEach(genre => {
        str += genre.name + '\n';
    });
    return str;
}

getComments = val => {
    let fragment = new DocumentFragment();
    val.comments.forEach(com => fragment.append(framingComment(com)));
    return fragment;
}

function framingComment(comment) {
    let li = document.createElement('li');
    li.append(comment.text);
    return li;
}

$('#addComment').click(function () {
    const bookId = getIdFromUrl();
    let comment = {text: $('#comment-text').val()};
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: 'POST',
        contentType: 'application/JSON',
        url: '/api/book/' + bookId + '/comment',
        data: JSON.stringify(comment),
        data_type: 'json',
        timeout: 600000,
    }).done(fetch('/api/book/' + bookId + '/view')
        .then(response => response.json())
        .then(() => document.getElementById('book-comments')
            .append(framingComment(comment))
        )
    )
});

$('#save-book').click(function (){
    let title = $('#title-input').val();
    let author = $('#author-input').val();
    let genre = $('#genre-input').val();
    let comment = $('#comment-input').val();

    if (title === "" || author === "") {
        document.getElementById('title-input').innerHTML='Это поле должно быть заполнено';
        document.getElementById('author-input').innerHTML='Это поле должно быть заполнено';
    } else {
    let book = {
        title: title,
        authors: [author],
        genres: [genre],
        comments: [comment]
    }
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: 'POST',
        contentType: 'application/JSON',
        url: '/api/book/create',
        data: JSON.stringify(book),
        data_type: 'json',
        timeout: 600000,
    }).done(fetch('/api/book/create')
        .then(() => document.getElementById('success').append('Книга успешно сохранена'))
    )
}})