# CRA (Create React App)

## 1. 튜토리얼

### 1.1. 튜토리얼

#### 1.1.1 CRA을 실행해보자

```shell
# Creating an App
npx create-react-app my-app
npm init react-app my-app
yarn create react-app my-app

cd my-app

# Scripts
npm start
yarn start
```

#### 1.1.2. 프로젝트 초기 상태

```
my-app/
├── README.md
├── node_modules/
├── package.json
├── .gitignore
├── public/
│   ├── favicon.ico
│   ├── index.html
│   ├── logo192.png
│   ├── logo512.png
│   ├── manifest.json
│   └── robots.txt
└── src/
    ├── App.css
    ├── App.js
    ├── App.test.js
    ├── index.css
    ├── index.js
    ├── logo.svg
    ├── serviceWorker.js
    └── setupTests.js
```

### 1.2 폴더 구조

- 프로젝트 빌드를 위해서, 아래와 같이 파일 이름을 지정해야 한다.
  - `public/index.html` : page template
  - `src/index.js` : JavaScript entry point
- 삭제하거나 다른 이름으로 변경해도 된다.
- `src`의 하위 디렉토리를 생성해도 된다. 빠른 rebuild를 위해 `src` 내의 파일만 webpack에 의해 처리된다. 그러므로, JS와 CSS 파일을 모두 `src`에 생성해야 한다.
- `public/index.html`은 `public` 내부 파일만 사용할 수 있다.
- 만들어진 프로젝트를 하위 디렉토리로 두어도 된다. 즉, Git을 설치하여 큰 repository 내에 프로젝트를 import를 할 때, 상위 레벨의 `.git` 파일을 따라가게 된다.

### 1.3. 사용 가능한 스크립트

- `npm start`
  - 개발 모드로 프로젝트를 실행한다.
  - [http://localhost:3000](http://localhost:3000) 에 프로젝트가 띄어진다.
  - 프로젝트를 수정하면 실시간으로 반영되며, 에러는 콘솔에 나타난다.
- `npm test`
  - test runner가 실행된다.
- `npm run build`
  - 운영에 배포를 위한 앱을 `build` 폴더에 build한다.
  - production build가 수행되는데, 파일 및 함수명을 해쉬 처리하는 등의 작업이 포함된다.
- `npm run eject`
  - 한 번 `eject`하면 되돌릴 수 없다.
  - 해당 프로젝트에 숨겨져 있는 모든 설정을 밖으로 추출해주는 명령
  - 모든 의존성을 `package.json`에서 살펴볼 수 있고, 커스터마이징이 가능하다.

### 1.4 지원하는 브라우저 및 기능

#### 1.5 지원하는 브라우저

- 대부분의 브라우저에서 지원하지만, IE 11 이하 버전은 [react-app-polyfill](https://github.com/facebook/create-react-app/blob/main/packages/react-app-polyfill/README.md)이 필요하다.
- 지원되는 브라우저를 지정하기
  1. `package.json`에 `browserslist` 키를 사용해 정의하면 된다.
     - ```json
       "browserslist": [
         "> 1%”,
         "last 2 versions",
         "not ie <= 10"
       ]
       ```
  2. `.browserslistrc` 파일을 생성하여 정의하면 된다.
     - ```
       > 1%
       last 2 versions
       not ie <= 10
       ```

### 1.5 새 Release 배포하기

- CRA는 두 패키지로 나눠 들어가게 된다.
  - `create-react-app` : 새 프로젝트를 생성하기위한 Global Command-Line 유틸리티
  - `react-scripts` : 생성된 프로젝트 내의 개발 의존성
