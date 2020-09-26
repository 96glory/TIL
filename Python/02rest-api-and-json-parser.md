# REST API & JSON Parsing

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

## Requests 모듈

### HTTP 메소드에 따른 요청

```python
r = requests.get('https://api.github.com/events')
r = requests.post('https://httpbin.org/post', data = {'key' : 'value'})
r = requests.put('https://httpbin.org/put', data = {'key' : 'value'})
r = requests.delete('https://httpbin.org/delete')
r = requests.head('https://httpbin.org/get')
r = requests.options('https://httpbin.org/get')
```

### 웹 요청 시 매개변수 전달 방법

```python
payload = {'key1' : 'value1', 'key2' : 'value2'}
r = requests.get('https://httpbin.org/get', params = payload)
```
```python
# https://search.naver.com/search.naver?query=glory
host = 'https://search.naver.com'
path = '/search.naver'
params = {'query' = 'glory'}

url = host + path
r = requests.get(url, params = params)
```

### 응답 데이터의 속성들

```python
print(r.status_code)    # 응답 상태 코드
print(r.url)            # 요청했던 url
print(r.text)           # 응답 데이터 (str), 주로 소스 코드나 문자 데이터, json
print(r.content)        # 응답 데이터 (byte), 음악, 비디오 등
print(r.encoding)       # 응답 데이터의 인코딩 방식
print(r.headers)        # 응답 데이터의 헤더
```

### 응답 데이터의 메소드

- 응답 데이터가 json일 경우에는 굳이 `dumps` 함수를 쓰지 않아도 바로 변환이 가능하다.
    ```python
    data = response.json()
    ```

- 응답 데이터의 상태코드 확인
    ```python
    data = r.status_code()
    data = r.raise_for_status()
    ```

### 웹 요청 데이터의 헤더 변경

```python
url = 'https://api.github.com/some/endpoint'
headers = {'user-agent': 'my-app/0.0.1'}
r = requests.get(url, headers=headers)
```
```python
import requests

url = 'http://www.ichangtou.com/#company:data_000008.html'
headers = {'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36'}

response = requests.get(url, headers=headers)
print(response.content)
```

### POST 방식으로 값이나 파일 전송

- 단순 객체
    ```python
    data = {'key':'value'}
    r = requests.post('https://httpbin.org/post', data = data )
    ```
- 특정 파일
    ```python
    url = 'https://httpbin.org/post'
    files = {'file': open('report.xls', 'rb')}
    r = requests.post(url, files = files)
    ```
- dict 객체를 json으로 전송하기
    ```python
    url = 'https://api.github.com/some/endpoint'
    payload = {'some': 'data'}
    r = requests.post(url, json=payload)
    ```

### curl 읽기

- curl option
    - `-d` : data 함께 전달할 파라미터값 설정하기
    - `-f` : files
    - `-j` : jsons
    - `-H` : hedears
    - `-A` : 헤더의 user-agent가 안내
    - `-X` : 요청 시 필요한 메소드 방식 안내
    - `-G` : 전송할 사이트 url 및 ip 주소
    - `-i` : 사이트의 Header 정보만 가져오기
    - `-I` : 사이트의 Header와 바디 정보를 함께 가져오기
    - `-u` : 사용자 정보
- [curl command를 python requests로 바꿔주는 사이트 링크](https://curl.trillworks.com/)
-  예시1 : 카카오 얼굴 인식 문서 with 웹 이미지
    ```shell
    curl -v -X POST "https://kapi.kakao.com/v1/vision/face/detect" \
    -d "image_url=https://t1.daumcdn.net/alvolo/_vision/openapi/r2/images/01.jpg" \
    -H "Authorization: KakaoAK kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk"
    ```
    ```python
    import requests

    url="https://kapi.kakao.com/v1/vision/face/detect"
    data = {'image_url':'https://t1.daumcdn.net/alvolo/\_vision/openapi/r2/images/01.jpg'}
    header = {'Authorization':'KakaoAK kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk'}

    res = requests.post(url, data=data, headers = header)
    print(res.status_code)
    '''
- 예시2 : 카카오 얼굴 인식 문서 with 로컬 이미지
    ```shell
    curl -v -X POST "https://kapi.kakao.com/v1/vision/face/detect" \
    -F "file=@sample_face.jpg" \
    -H "Authorization: KakaoAK kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk"
    ```
    ```python
    import requests

    url="https://kapi.kakao.com/v1/vision/face/detect"
    files= {'file' : open('sample_face.jpg','rb').read() }
    header = {'Authorization':'KakaoAK kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk'}

    res = requests.post(url, files= files, headers = header)
    print(res.status_code)
    ```