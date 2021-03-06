## Stock Portfolio Manager App using Java Spring

#### HOW TO RUN

In the project folder (the folder containing this file)
open terminal window and type:

java -jar target/FyberChallenge-0.0.1-SNAPSHOT.jar

The application should should start.

#### HOW TO USE

The application reads the "stocks stream" from the file called "stocks.ser".
This File was generated by me after confirmation from Dori that this "stock stream" is
a file that we create.
The file contains the stocks which are created in the StockStreamCreator (feel free to
add more stocks as you would like). The streamCreator is called from
"FyberChallengeApplication.java" and does not have to be generated each time. (it is
generated each time in order for your changes to take affect)

ASSUMPTIONS:

1.
If a stock hasn't received an update, in say 7 days, this means that it's value 
(or price) have stayed the same throught these 7 days. Thus if a stock received a last 
update 10 days ago and we 5 days ago and we want to look at the value change in the 
last 7 days we take its value 5 days ago and subtract it's value from 10 days ago 
(since the value 10 days ago was also the value 7 days ago)

2.
As far as I understand it, in a portfolio update what differce registering a completly
new portfolio from just adding to the current one is that one of the values in the 
input (post body) is a stock which isn't present at the current portfolio.
(current portfolio - portfolio before the update)
If such stock exists we entirely replace the user's portfolio.
Otherwise we just buy and sell stocks from the current portfolio.

ENDPOINTS:

1. Create Portfolio:
address: "localhost:8080/create" (POST)
post body: a JSON object of the format:
    {"stocks":[{"stock":"GOOG","amount":"8"}, {"stock":"FB", "amount":"1"}]}
    stocks is an array of stock each one composed of "stock" and "amount".
return: 
    {"id": 8}
    the id of the new client

2. Update Portfolio:
address: "localhost:8080/{id}/update" (POST)
    id is the client id which portfolio's we intend to update.
post body: 
    { "stocks":[{"stock":"FB","amount":"4","buy":false}, {"stock":"TEVA", "amount":"2","buy":false}]}
    stocks is an array of stock each one composed of "stock","amount" and "buy".
    buy indicated whether we are buying or selling the amount (if not specified
    defaults to true).
    In addition when doing a new portfolio update the "buy" parameter will be ignored 
    and the request will act like a create request.
return:
    void

3. Portfolio Value:
address: "localhost:8080/{id}/portfolio-value" (GET)
    id is the client id.
return:
    11500.543 (float)
    a float value which is the portfolio's value.

4. Performance Recommendation:
address: "localhost:8080/{id}/recommend/performance" (GET)
    id is the client id we recommend to.
return:
    AAPL (String)
    a string value which is a stock name. (in this case the stock of Apple Inc.)

5. Most Stable Recommendation:
address: "localhost:8080/{id}/recommend/stable" (GET)
    id is the client id we recommend to.
return:
    AAPL (String)
    a string value which is a stock name. (in this case the stock of Apple Inc.)

6. Best Recommendation:
address: "localhost:8080/{id}/recommend/best" (GET)
    id is the client id we recommend to.
    return:
AAPL (String)
    a string value which is a stock name. (in this case the stock of Apple Inc.)


NOTES:

1. Unknown Recommendation Strategy:
all "localhost:8080/{id}/recommend/**" address are mapped to an error message page on purpose





















