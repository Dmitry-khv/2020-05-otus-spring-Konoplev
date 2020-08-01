###Создать приложение хранящее информацию о книгах в библиотеке
<p>
Цель: использовать возможности Spring JDBC и spring-boot-starter-jdbc для подключения к реляционным базам данных 
<p>
Результат: приложение с хранением данных в реляционной БД, которое в дальнейшем будем развивать
<p>
1. Использовать Spring JDBC и реляционную базу (H2 или настоящую реляционную БД). Настоятельно рекомендуем использовать NamedParametersJdbcTemplate<p>
2. Предусмотреть таблицы авторов, книг и жанров.<p>
3. Предполагается отношение многие-к-одному (у книги один автор и жанр). Опциональное усложнение - отношения многие-ко-многим (у книги может быть много авторов и/или жанров).<p>
4. Интерфейс выполняется на Spring Shell (CRUD книги обязателен, операции с авторами и жанрами - как будет удобно).<p>
5. Скрипт создания таблиц и скрипт заполнения данными должны автоматически запускаться
с помощью spring-boot-starter-jdbc.<p>
6. Покрыть тестами, насколько это возможно.<p>