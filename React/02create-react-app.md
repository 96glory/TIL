# CRA (Create React App)

- [CRA (Create React App)](#cra-create-react-app)
  - [1. 튜토리얼](#1-튜토리얼)
    - [1.1. 튜토리얼](#11-튜토리얼)
      - [1.1.1 CRA을 실행해보자](#111-cra을-실행해보자)
      - [1.1.2. 프로젝트 초기 상태](#112-프로젝트-초기-상태)
    - [1.2. 폴더 구조](#12-폴더-구조)
    - [1.3. 사용 가능한 스크립트](#13-사용-가능한-스크립트)
    - [1.4. 지원하는 브라우저 및 기능](#14-지원하는-브라우저-및-기능)
      - [1.4.1. 지원하는 브라우저](#141-지원하는-브라우저)
    - [1.5. 새 Release 배포하기](#15-새-release-배포하기)
  - [2. Styles and Assets](#2-styles-and-assets)
    - [2.1. Stylesheet 추가하기](#21-stylesheet-추가하기)
    - [2.2. CSS Modules Stylesheet 추가하기](#22-css-modules-stylesheet-추가하기)
    - [2.3. Sass Stylesheet 추가하기](#23-sass-stylesheet-추가하기)
    - [2.4. CSS Reset 추가하기](#24-css-reset-추가하기)
    - [2.5. Post-Processing CSS](#25-post-processing-css)
    - [2.6. 이미지, 폰트, 파일 추가하기](#26-이미지-폰트-파일-추가하기)
      - [2.6.1. bmp, gif, jpg, jpeg, png 추가하기](#261-bmp-gif-jpg-jpeg-png-추가하기)
      - [2.6.2. SVG 추가하기](#262-svg-추가하기)
    - [2.7. .graphql Files 로딩하기](#27-graphql-files-로딩하기)
    - [2.8. Public Folder 사용하기](#28-public-folder-사용하기)
      - [2.8.1. HTML 변경하기](#281-html-변경하기)
      - [2.8.2. 모듈 시스템 외부에 있는 Asset 추가하기](#282-모듈-시스템-외부에-있는-asset-추가하기)
      - [2.8.3 `public` 폴더를 사용할 때](#283-public-폴더를-사용할-때)
    - [2.9. 코드 분할 (Code Splitting)](#29-코드-분할-code-splitting)
  - [3. App 빌드하기](#3-app-빌드하기)
    - [3.1. Dependency 설치하기](#31-dependency-설치하기)
    - [3.2. Importing a Component](#32-importing-a-component)
      - [3.2.1. Import & Export](#321-import--export)
      - [3.2.2. Absolute Imports](#322-absolute-imports)
    - [3.3. 전역변수 사용하기](#33-전역변수-사용하기)
    - [3.4. Bootstrap 사용하기](#34-bootstrap-사용하기)
    - [3.5. Flow 사용하기](#35-flow-사용하기)
    - [3.6. TypeScript 사용하기](#36-typescript-사용하기)
      - [3.6.1. 설치](#361-설치)
    - [3.7. Relay 사용하기](#37-relay-사용하기)
    - [3.8. Router 사용하기](#38-router-사용하기)
    - [3.9. 커스텀 환경 변수 설정하기](#39-커스텀-환경-변수-설정하기)
      - [3.9.1. 커스텀 환경 변수 설정하기](#391-커스텀-환경-변수-설정하기)
      - [3.9.2. HTML 내의 환경 변수 참조하기](#392-html-내의-환경-변수-참조하기)
      - [3.9.3. Shell에 임시 환경 변수 추가하기](#393-shell에-임시-환경-변수-추가하기)
      - [3.9.4. `.env`에 환경 변수 추가하기](#394-env에-환경-변수-추가하기)
    - [3.10. Progressive Web App 만들기](#310-progressive-web-app-만들기)
    - [3.11. 성능 측정하기](#311-성능-측정하기)
    - [3.12. 운영 빌드](#312-운영-빌드)
      - [3.12.1. chunk](#3121-chunk)
      - [3.12.2. 정적 파일 캐싱](#3122-정적-파일-캐싱)

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

### 1.2. 폴더 구조

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

### 1.4. 지원하는 브라우저 및 기능

#### 1.4.1. 지원하는 브라우저

- 대부분의 브라우저에서 지원하지만, IE 11 이하 버전은 [react-app-polyfill](https://github.com/facebook/create-react-app/blob/main/packages/react-app-polyfill/README.md)이 필요하다.
- 지원되는 브라우저를 지정하기
  1. `package.json`에 `browserslist` 키를 사용해 정의하면 된다.
     - ```
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

### 1.5. 새 Release 배포하기

- CRA는 두 패키지로 나눠 들어가게 된다.
  - `create-react-app` : 새 프로젝트를 생성하기위한 Global Command-Line 유틸리티
  - `react-scripts` : 생성된 프로젝트 내의 개발 의존성

## 2. Styles and Assets

### 2.1. Stylesheet 추가하기

- CRA 프로젝트는 모든 asset을 관리하기 위해 [webpack](https://webpack.js.org/)을 사용한다. webpack은 `import`의 개념을 확장하는 방식으로 프로젝트를 커스터마이징할 수 있다. CSS 기반 JS를 표현하기 위해, 아래와 같이 JS에 CSS를 `import`하면 된다.

  - `Button.css`
    ```css
    .Button {
      padding: 20px;
    }
    ```
  - `Button.js`

    ```javascript
    import React, { Component } from "react";
    import "./Button.css";

    class Button extends Component {
      render() {
        // You can use them as regular CSS styles
        return <div className="Button" />;
      }
    }
    ```

### 2.2. CSS Modules Stylesheet 추가하기

- CRA는 CSS Modules(파일 형식 : `[name].module.css`)를 지원한다.
- CSS Modules
  - 모든 클래스 이름과 애니메이션 이름이 기본적으로 로컬로 범위가 지정되는 CSS 파일
  - CSS Modules는 `[filename]\_[classname]\_\_[hash]` 형식의 유일한 클래스 이름을 자동으로 만듦으로써 CSS의 범위를 지정할 수 있게 한다.
  - [왜 사용하는가?](https://css-tricks.com/css-modules-part-1-need/)
- `Button.module.css`

  ```css
  .error {
    background-color: red;
  }
  ```

  `another-stylesheet.css`

  ```css
  .error {
    color: red;
  }
  ```

  `Button.js`

  ```javascript
  import React, { Component } from "react";
  import styles from "./Button.module.css"; // Import css modules stylesheet as styles
  import "./another-stylesheet.css"; // Import regular stylesheet

  class Button extends Component {
    render() {
      // reference as a js object
      return <button className={styles.error}>Error Button</button>;
    }
  }
  ```

  Result : No clashes from other .error class names

  ```html
  <!-- This button has red background but not red text -->
  <button class="Button_error_ax7yz">Error Button</button>
  ```

### 2.3. Sass Stylesheet 추가하기

### 2.4. CSS Reset 추가하기

### 2.5. Post-Processing CSS

### 2.6. 이미지, 폰트, 파일 추가하기

#### 2.6.1. bmp, gif, jpg, jpeg, png 추가하기

- Webpack에서 이미지, 폰트, 파일과 같은 정적 asset을 사용하는 것은 CSS와 유사하다.
- JS 모듈에 바로 파일로 `import`할 수 있다. CSS import와는 다르게, 파일을 import하면 string value가 도출된다. 이 값은 코드에서 참조할 수 있는 최종 경로이다.
- 서버의 요청 횟수를 줄이기 위해, 10,000바이트 미만의 이미지를 가져오면 경로 대신 데이터 URI가 반환된다. (bmp, gif, jpg, jpeg, png) `IMAGE_INLINE_SIZE_LIMIT` 환경 변수를 설정하여 10,000바이트 임계값을 제어할 수 있다.
- 아래 프로젝트가 빌드될 때, webpack은 image 파일을 build 폴더로 옮기고, 올바른 경로를 제공하는 것을 보장한다.

  ```javascript
  import React from "react";
  import logo from "./logo.png"; // Tell webpack this JS file uses this image

  console.log(logo); // /logo.84287d09.png

  function Header() {
    // Import result is the URL of your image
    return <img src={logo} alt="Logo" />;
  }

  export default Header;
  ```

- 또는, 아래 CSS 처럼 작성해도 된다.
  ```css
  .Logo {
    background-image: url(./logo.png);
  }
  ```
  - webpack은 CSS 내에서 관련 있는 모든 module 레퍼런스를 찾아서 (보통 `./`로 시작하는), 최종 경로로 바꿔준다. 만일 레퍼런스에 해당하는 파일이 없다면, 컴파일 에러가 발생한다.
  - 컴파일된 파일의 최종 파일 이름은 webpack의 hash 값에 의해 생성된다. 만일 파일이 변경되면 webpack은 다른 hash 값을 생성해주므로 long-term caching이 발생하지 않는다.

#### 2.6.2. SVG 추가하기

- SVG 파일은 위 방식대로 추가하지 못하므로, 아래 방식을 사용한다.

  ```javascript
  // img src에 이용할 수 있지만, 아래처럼 svg를 React 컴포넌트로 사용해야 React 적으로 custom 하여 사용할 수 있다.
  import { ReactComponent as Logo } from "./logo.svg";

  function App() {
    return (
      <div>
        {/* Logo is an actual React component */}
        <Logo />
      </div>
    );
  }
  ```

### 2.7. .graphql Files 로딩하기

### 2.8. Public Folder 사용하기

#### 2.8.1. HTML 변경하기

- `public` 폴더는 HTML 파일이 들어있고, 이를 변경하여 사용할 수 있다. 컴파일된 코드가 포함된 `<script>` 태그는 빌드 과정 중 자동으로 추가된다.

#### 2.8.2. 모듈 시스템 외부에 있는 Asset 추가하기

- 보통 모듈 시스템 외부에 있는 Asset을 추가하는 대신, "2.6. 이미지, 폰트, 파일 추가하기"와 "2.1. Stylesheet 추가하기"와 같이 JS 파일의 Asset을 import하는 것을 추천한다.

  - 왜냐하면,

    1. Script와 Stylesheet들이 최소화되고, 한꺼번에 묶여 추가적인 네트워크 요청을 피할 수 있다.
    2. 파일의 부재는 사용자에게 404 Error를 발생시키지 않고 컴파일 에러를 발생시킨다.
    3. content hash를 포함한 최종 파일 이름은 long-term caching 을 발생시키지 않는다.

- Escape Hatch
  - `public`에 파일을 추가하면, 이 파일들은 webpack에 의해 가공되지 않는다. 대신, 변경되지 않은 채로 build 폴더로 복사된다. `public` 폴더의 asset을 참조하려면 환경 변수 `PUBLIC_URL`을 사용해야 한다. 또한, `public` 폴더 내의 asset에서만 `PUBLIC_URL`을 사용할 수 있다.
  - 예를 들어, `index.html`에 다음과 같이 작성할 수 있다.
    ```html
    <link rel="icon" href="%PUBLIC_URL%/favicon.ico" />
    ```
  - 하지만,
    1. Script와 Stylesheet들이 최소화되지 않는다.
    2. 파일의 부재는 404 Error를 발생시킬 수 있다.
    3. 최종 파일 이름은 content hash를 포함하지 않으므로, query arguement를 추가하거나 변경 시 매번 이름을 변경해주어야 한다.

#### 2.8.3 `public` 폴더를 사용할 때

- build output에 특정 이름의 파일이 필요할 때. (예 : `manifest.webmanifest`)
- 수천 개의 이미지가 있고, 그들의 경로를 동적으로 참조해야할 때
- 번들 코드 외부에 `pace.js`와 같은 작은 스크립트 파일을 포함하고 싶을 때
- 일부 라이브러리는 webpack과 호환되지 않을 때. (`<script>` 태그에 포함시켜야 한다.)

### 2.9. 코드 분할 (Code Splitting)

> [React의 코드 분할](https://ko.reactjs.org/docs/code-splitting.html)

- app의 코드 전부를 다운로드 받는 대신, 사용자가 로드하는 코드만 다운로드 받을 수 있게 한다.
- 코드 분할은 dynamic import를 통해 setup된다. `import()`는 모듈 이름을 아규먼트로 사용하고 항상 모듈의 namespace object로 확인되는 `Promise`를 반환한다.
- 아래와 같이 작성하면, `moduleA.js`와 모든 고유한 dependency가 별도의 청크로 만들어지며, 'Load' 버튼을 클릭한 뒤에만 로드된다.
- `moduleA.js`

  ```javascript
  const moduleA = "Hello";

  export { moduleA };
  ```

  `App.js`

  ```javascript
  import React, { Component } from 'react';

  class App extends Component {
      handleClick = () => {
          import('./moduleA')
            .then(({ module A }) => {
                // Use moduleA
            })
            .catch(err => {
                // Handle failure
            })
      };

      render() {
          return (
              <div>
                <button onClick={this.handleClick}>Load</button>
              </div>
          );
      }
  }
  ```

## 3. App 빌드하기

### 3.1. Dependency 설치하기

- 생성된 프로젝트는 React와 ReactDOM을 dependency로 가진다. 또한 CRA에서 사용하는 script 또한 개발 환경의 dependecy로 갖는다.
- `npm install --save react-router-dom`과 `yarn add react-router-dom`등을 통해 새 라이브러리를 추가할 수 있다.

### 3.2. Importing a Component

#### 3.2.1. Import & Export

- ES6의 문법 `require()`과 `module.exports`가 존재하지만, `import`와 `export`를 사용하는 것을 추천한다.
- `Button.js`

  ```javascript
  import React, { Component } from "react";

  class Button extends Component {
    render() {
      // ...
    }
  }

  export default Button;
  ```

  `DangerButton.js`

  ```javascript
  import React, { Component } from 'react;
  import Button from './Button';

  class DangerButton extends Component {
      render() {
          return <Button color="red" />;
      }
  }

  export default DangerButton;
  ```

- [Default Export와 Named Export의 차이](https://medium.com/@_diana_lee/default-export%EC%99%80-named-export-%EC%B0%A8%EC%9D%B4%EC%A0%90-38fa5d7f57d4)
  - Default로 Export된 모듈은 하나의 파일에서 단 하나의 변수 또는 클래스 등만 export할 수 있다.
  - Named로 Export된 모듈은 한 파일 내에서 여러 변수 또는 클래스 등을 export할 수 있다.

#### 3.2.2. Absolute Imports

- 프로젝트에서 절대 경로를 사용하여 모듈을 import할 수 있다. 프로젝트 root의 `jsconfig.json`이나 `tsconfig.json`에서 설정할 수 있다.
- 만약 아래와 같이 설정하면,
  ```json
  /* jsconfig.json */
  {
    "compilerOptions": {
      "baseUrl": "src"
    },
    "include": ["src"]
  }
  ```
  `src`가 절대경로가 된다. 만약 `src/components/Button.js`에 모듈을 위치시켰다면, `import Button from 'components/Button';`를 통해 모듈 import하면 된다.

### 3.3. 전역변수 사용하기

### 3.4. Bootstrap 사용하기

### 3.5. Flow 사용하기

### 3.6. TypeScript 사용하기

- TypeScript는 JavaScript의 상위 집합체로, JavaScript를 컴파일할 수 있다.

#### 3.6.1. 설치

- TypeScript을 포함된 CRA를 처음 설치하고자 할 때, 아래 명령어를 실행하여야 한다.

  ```shell
  npx create-react-app my-app --template typescript
  # or
  yarn create react-app my-app --template typescript
  ```

- 기존 CRA프로젝트에 TypeScript를 추가하고자 할 때,

  1.  먼저 TypeScript를 아래 명령어를 통해 설치한다.

      ```shell
      npm install --save typescript @types/node @types/react @types/react-dom @types/jest

      # or

      yarn add typescript @types/node @types/react @types/react-dom @types/jest
      ```

  2.  JavaScript 파일을 TypeScript 파일 확장자로 수정하고, 서버를 재시작하라.

### 3.7. Relay 사용하기

### 3.8. Router 사용하기

- [React Router](https://ko-de-dev-green.tistory.com/39)

  - 페이지 로딩 없이 페이지에 필요한 컴포넌트를 불러와 렌더링하여 보여주도록 도와준다.
  - REact의 강점은 Single Plage Application이다. 이는 하나의 index HTML을 두고 컴포넌트만 변경시켜 각각 개별적으로 사용하고 업데이트 하는 것이 가능하다.

- 아래 명령어 실행
  - `npm install --save react-router-dom`
  - `yarn add react-router-dom`

### 3.9. 커스텀 환경 변수 설정하기

#### 3.9.1. 커스텀 환경 변수 설정하기

- 기본적으로 `NODE_ENV`와 `REACT_APP_`로 시작하는 다른 환경 변수가 존재한다.
- 환경 변수를 커스텀할 수 있다. 반드시 `REACT_APP_`으로 시작하여야 한다.
- 환경 변수는 빌드 타임에 생성된다.
- CRA는 정적 HTML/CSS/JS 번들을 생성하므로 런타임에 읽을 수 없다. 런타임에 읽기 위해서 HTML을 서버의 메모리에 로드하고 런타임에 PlaceHolder를 교체해야 한다.
- `NODE_ENV`는 npm 환경에 따라 달라진다.
  - `npm start` : `development`
  - `npm test` : `test`
  - `npm run build` : `production`
- 환경 변수는 `process.env` 에 정의된다. 만일 커스텀 환경 변수 명이 `REACT_APP_TEST`라면, JS에서 `process.env.REACT_APP_TEST`로 사용할 수 있다.
  ```javascript
  render() {
    return (
      <div>
        <small>You are running this application in <b>{process.env.NODE_ENV}</b> mode.</small>
          <form>
            <input type="hidden" defaultValue={process.env.REACT_APP_TEST} />
            // build 중에 process.env.REACT_APP_TEST는 실제 환경변수 값으로 교체된다.
          </form>
      </div>
    );
  }
  ```

#### 3.9.2. HTML 내의 환경 변수 참조하기

- HTML 내에서 `REACT_APP_`으로 시작하는 환경 변수에 접근할 수 있다.
  ```html
  <title>%REACT_APP_WEBSITE_NAME%</title>
  ```

#### 3.9.3. Shell에 임시 환경 변수 추가하기

- Windows (cmd.exe)
  - `set "REACT_APP_TEST=abcdef" && npm start`
- WIndows (Powershell)
  - `($env:REACT_APP_TEST = "abcdef") -and (npm start)`
- Linux, macOS (Bash)
  - `REACT_APP_TEST=abcdef npm start`

#### 3.9.4. `.env`에 환경 변수 추가하기

- 프로젝트 루트에 `.env` 확장자 파일을 추가한다.
  - `REACT_APP_TEST=abcdef`
- `.env`

  - `.env` : Default.
  - `.env.local` : Local overrides. This file is loaded for all environments except test.
  - `.env.development`, `.env.test`, `.env.production` : Environment-specific settings.
  - `.env.development.local`, `.env.test.local`, `.env.production.local` : Local overrides of environment-specific settings.

- npm 실행 명령어에 따라 `.env`의 우선순위가 달라진다.

  - `npm start` : `.env.development.local`, `.env.local`, `.env.development`, `.env`
  - `npm run build` : `.env.production.local`, `.env.local`, `.env.production`, `.env`
  - `npm test` : `.env.test.local`, `.env.test`, `.env`

- `.env`는 `$`를 통해 확장할 수 있다.

  ```properties
  REACT_APP_VERSION=$npm_package_version
  # also works:
  # REACT_APP_VERSION=${npm_package_version}

  DOMAIN=www.example.com
  REACT_APP_FOO=$DOMAIN/foo
  REACT_APP_BAR=$DOMAIN/bar
  ```

### 3.10. Progressive Web App 만들기

### 3.11. 성능 측정하기

### 3.12. 운영 빌드

#### 3.12.1. chunk

- 명령어 `npm run build`는 app의 운영 빌드 파일을 `build` 디렉토리에 저장해준다. `build/static` 경로에는 JS/CSS 파일이 존재한다. 각각의 파일 이름은 고유한 hash 값을 포함한다. 이 hash를 통해 long-term caching을 적용할 수 있게 한다.
- 새로 만든 CRA의 운영 빌드를 실행할 때, `build/static/js` 디렉터리에 수많은 `.js` 파일(청크라고 함)이 있다.
  - `main.[hash].chunk.js`
    - 애플리케이션 코드에 해당. `App.js`, etc.
  - `[number].[hash].chunk.js`
    - vendor code, 혹은 코드 분할 청크라고 불린다.
    - vendor code는 `node_modules`로부터 import된 모듈이 포함된다.
    - 코드 분할을 통해 애플리케이션 로딩 성능을 향상시키기 위해 장기적인 캐싱 기술을 사용할 수 있다.
  - `runtime-main.[hash].js`
    - 애플리케이션을 실행 및 로드하기 위해 사용하는 webpack 런타임 로직의 작은 청크다.
    - 추가적인 네트워크 요청을 줄이기 위해 `build/index.html`에 위 내용들이 포함된다.
    - `index.html`에 추가하는 대신 이 청크를 로드하고 싶다면, `INLINE_RUNTIME_CHUNK=false`를 명시하여야 한다.
  - 만일 코드 분할을 사용하고 있다면, 추가적인 청크가 `build/static` 폴더에 존재할 것이다.

#### 3.12.2. 정적 파일 캐싱

- `build/static` 경로 내의 파일들은 파일 명에 hash 값이 추가된다. 파일 내용이 변경되지 않은 경우 브라우저가 그 Asset을 재 다운로드하지 않도록 aggresive caching techniques를 사용할 수 있다. 이후 빌드에서 파일의 내용이 변경되면, 파일의 해쉬값이 달라진다.
- 사용자에게 최고의 성능을 내기 위해 `index.html`의 [Cache-Control](https://jakearchibald.com/2016/caching-best-practices/) header를 적절히 설정해야 한다. 이 헤더를 사용하면 CDN뿐만 아니라 브라우저가 정적 Asset을 캐시하는 시간을 제어할 수 있다.
- `Cache-Control: max-age=31536000` 와 `Cache-Control: no-cache`를 사용하면 브라우저가 항상 업데이트된 `index.html` 파일을 확인하고 모든 `build/static` 파일을 1년 동안 캐시할 수 있는 안전한 상태가 된다.
