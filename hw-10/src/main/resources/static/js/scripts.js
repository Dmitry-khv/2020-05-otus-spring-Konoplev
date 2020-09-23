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

$(document).ready(function ($) {
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
        }).done(fetch('/api/book/' + bookId)
            .then(response => response.json())
            .then(() => document.getElementById('book-comments')
                .append(framingComment(comment))
            )
        )
    });
});

function framingComment(comment) {
    let li = document.createElement('li');
    li.append(comment.text);
    return li;
}