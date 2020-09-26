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

url = "https://swapi.dev/api/people/1/"
data = requests.get(URL).json()
json_data = json.dumps(data)
```

- `url`에는 통신하고 있는 서버 / 웹사이트의 주소가 들어가면 된다.
- `json()` 함수를 통해 받아온 `data`는 딕셔너리 객체로 받아오게 되므로, json 형태의 `str`로 바꾸고 싶다면 `dumps()` 함수를 사용하면 된다.

## JSON 값을 출력하기

```python
import json, requests

url = "https://swapi.dev/api/people/1/"
data = requests.get(URL).json()

# dictionary -> json (str)
json_data = json.dumps(data)
print(type(json_data))
print(json_data)

# pretty print
json_pretty_data = json.dumps(data, indent=2)
print(type(json_pretty_data))
print(json_pretty_data)

# json -> dictionary
dict_data = json.loads(json_data)
print(type(dict_data))
print(dict_data)
```

## 가져온 JSON 파일을 Parsing하기

- key 값의 이름이 `people`이면
    ```python
    value = data["people"]
    ```
- key 내의 모든 value를 순회하고 싶다면
    ```python
    for i in range(len(data["vehicles"])):
        print(data["vehicles"][i])
    ```