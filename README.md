Функциональные требования:
• Сервис получает котировки
• Сервис хранит историю полученных котировок в БД
• Сервис рассчитывает elvl (правила ниже)
• Сервис предоставляет elvl по isin
• Сервис предоставляет перечень всех elvls

Использованы технологии.
- Java 8 
- Gradle 
- Spring Boot
- Spring Test

Описание методов:
### Создание нового объекта/обновление существующего
URL: localhost:8878/api/v1/quotes/

Метод: POST

Получает параметры:  "isin" - строка 12 символов, обязательный параметр, "bid" - число, необязательный параметр, "ask" - число, обязательный параметр

Возвращает объект elvl: "isin" - строка 12 символов, "elvl" - число.

Пример вызова: 

POST localhost:8878/api/v1/quotes/
Body: {
	"isin": "RU000A0JX0J2",
	"bid": 103.20,
	"ask": 105.09
      }
      
Успешный ответ: 

HTTP Code: 200
	Body: {
		"isin": "RU000A0JX0J2",
		"elvl": "103.2"
		     }
         
Ответ с ошибкой:

HTTP Code: 400
.... "defaultMessage": "размер должен быть между 12 и 12" ....

### Получение elvl по ISIN
URL: localhost:8878/api/v1/quotes/{isin}

Метод: GET

Получает параметры:  "isin" - строка 12 символов

Возвращает объект elvl: "isin" - строка 12 символов, "elvl" - число.

Пример вызова: 

GET localhost:8878/api/v1/quotes/RU000A0JX0J2
      
Успешный ответ: 

HTTP Code: 200
	Body: {
		"isin": "RU000A0JX0J2",
		"elvl": "103.2"
		}
         
Ответ с ошибкой:

HTTP Code: 400
"message": "Please check the params"

HTTP Code: 404
"message": "No elvls was found"

### Получение всех elvl 
URL: localhost:8878/api/v1/quotes/

Метод: GET

Возвращает коллекцию объектов elvl

Пример вызова: 

GET localhost:8878/api/v1/quotes/
      
Успешный ответ: 

HTTP Code: 200
	Body: [
          {
		"isin": "RU000A0JX0J2",
		"elvl": "103.2"
           }
         ]
         
Ответ с ошибкой:

HTTP Code: 404
"message": "No elvls was found"

### Удаление elvl и quotes по isin
Реализовано для очистки базы после выполнения интеграционных тестов.
Для будущего использования можно сделать подключаемым/отключаемым

URL: localhost:8878/api/v1/quotes/{isin}

Метод: DELETE

Получает параметр:  "isin" - строка 12 символов

Пример вызова: 
localhost localhost:8878/api/v1/quotes/RU000A0JX0J2
      
Успешный ответ: 
HTTP Code: 204
	       
Ответ с ошибкой:
HTTP Code: 400
"message": "Please check the params"
