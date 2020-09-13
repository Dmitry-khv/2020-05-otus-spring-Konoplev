$.getJSON('/api/books').done(books => {
    $.each(books, (idx, val) => {
        let bookId = val.id;
        let bookTitle = val.title;

        $('#books').append(
            '<tr><td>' + bookId + '</td>' +
            '<td>' + bookTitle + '</td>' +
            '<td>' + getAuthors(val) + '</td>' +
            '<td>' + getGenres(val) + '</td></tr>'
        )
    })
})

let getAuthors = val => {
    let str = '';
    val.authors.forEach(author => {
        str += author.name + '<br>';
    })
    return str;
}

let getGenres = val => {
    let str = '';
    val.genres.forEach(genre => {
        str += genre.name + '<br>';
    })
    return str;
}