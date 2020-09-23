# JSON Parsing

## JSON

```json
{
    "key1" : "value",
    "key2" : 100
}
```

## REST API를 이용해서 받아온 값을 JSON 형태로 이용하기

```python
import json, requests
data = requests.get(URL).json()

# URL에는 통신하고 있는 서버 / 웹사이트의 주소가 들어가면 된다.
```

## 가져온 JSON 파일을 Parsing하기

- key 값의 이름이 `people`이면
    ```python
    value = data["people"]
    ```