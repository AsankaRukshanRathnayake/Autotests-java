# Autotests-java
The final project for the Autotest in Java course.

Цель проекта: 
Автоматизировать все основные тестовые сценарии сайта, чтобы на регресс уходило меньше времени.

О работе:
- В работе использовались JUnit и Selenium WebDriver;
- Работа разделена на страницы сайта (Главная страница, Каталог товаров, Корзина, Регистрация и авторизация,
Оформление заказа, Личный кабинет), которые соответствуют тестовым классам;
- Во всех классах подготовительная работа по настройке драйвера вынесена в метод setUp, отмеченный @Before,
а закрытие браузера (driver.quit()) вынесено в метод tearDown, отмеченный @After.;
- Название тестовых методов соответствует содержимому, использован шаблон наименования тестов: StateUnderTest_ExpectedBehavior;
- Локаторы устойчивы к изменениям вёрстки и выделены в отдельный блок кода для дальнейшего переиспользования;
- Использован AAA-паттерн разметки теста.


Итоги:
- Были выделены основные сценарии для написания тестов;
- Было создано 60 тестов, 2 из которых нашли баг в форме авторизации (public void authorizationWithMisprintedName_AlertAppears(),
public void authorizationWithMisprintedPassword_AlertAppears());
- Для страницы каталога товаров проведен расчёт экономической целесообразности ее автоматизации
(Через 77 запусков тестов тестировать автоматизировано будет выгоднее);
- Выполнена цель проекта.
