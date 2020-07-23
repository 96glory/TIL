# 냅색 알고리즘 (Knapsack Algorithm)
## 요소가 무제한인 경우
* 가방에 담을 수 있는 무게의 한계값과 보석의 종류가 주어진다. <br>
  보석의 무게와 가치가 주어진다. <br>
  보석은 종류별로 무한하게 있다고 가정하자. <br>
  가방에 보석을 담을 때, 최대의 가치를 출력하시오.
* 입력 예제 <br>
  4 11 <br>
  5 12  <br>
  3 8 <br>
  6 14 <br>
  4 8 <br>
* 출력 예제 <br>
  28

* 접근 및 해결 포인트
  * dp[index]에 무엇을 저장할까?
    * 가방에 __index만큼의 무게__ 까지 담을 수 있을 때의 보석 초대 가치를 저장
  * 보석을 기준으로 for문이 도는데, 그 보석이 dp에 영향을 줄수 있을 때가 index >= 보석.weight이다.  <br>
    그 index부터 dp[index - 보석.weight] + 보석.value를 도출해낼 수 있다. (기존의 dp[index])와 (dp[index - 보석.weight] + 보석.value)를 비교하여 큰 값으로 dp[index]를 업데이트한다.
    
* 과정
	![그림1](https://user-images.githubusercontent.com/52440668/88272849-4e01c400-cd14-11ea-95b9-075a3e3d8d54.png)

* 코드
```cpp
#include <bits/stdc++.h>
using namespace std;

int n, m, w, v;

int main(){
	ios_base::sync_with_stdio(false);
	
	cin >> n >> m;
	vector<int> dp(m + 1, 0);
	
	for(int i = 0; i < n; i++){
		cin >> w >> v;
		for(int j = w; j < m; j++){
			dp[j] = max(dp[j], dp[j - w] + v);
		}
	}
	
	cout << dp[m];
}
```
## 요소가 제한적인 경우
* 첫 줄에 문제의 갯수와 시험 시간이 주어진다. <br>
  둘째 줄부터 문제의 소요 시간과 얻는 점수가 주어진다. <br>
  제한 시간 M 분 안에 N 개의 문제 중 특정 문제를 골라 풀어 최대 점수를 얻게 하시오. <br>
  단, 주어진 문제는 한 개만 풀 수 있다.
* 입력 예제 <br>
	5 20 <br>
	10 5 <br>
	25 12 <br>
	15 8 <br>
	6 3 <br>
	7 4
* 출력 예제 <br>
	41
#### naive solution
* 접근 및 해결 포인트
	* dp[i][j]에 무엇을 저장할까?
		* i번째까지의 문제 종류까지 있고, 시험 시간이 총 j 분일 때의 최대 점수를 저장
* 과정
	![그림2](https://user-images.githubusercontent.com/52440668/88278784-ef414800-cd1d-11ea-8163-3a698d7be8ef.png)
* 한계
	* 위 해결책은 dp를 2차원 배열로 선언하였다. <br>
	  문제의 시간이 커질수록 dp가 메모리를 차지하는 크기가 커진다. <br>
	  고로, 시간이 부족할 수 있다. 1차원 배열로 해결하는 방식은 없을까?

#### improved solution
* 접근 및 해결 포인트
	* dp[i]에 무엇을 저장할까?
		* 시험 시간이 총 i 분일 때의 최대 점수를 저장
	* 뒤에서부터 앞으로 전진하면서 dp하면 중복되지 않는다!
* 과정
	![그림3](https://user-images.githubusercontent.com/52440668/88280044-16991480-cd20-11ea-9ee2-c9fd349b6e11.png)
* 코드
```cpp
#include <bits/stdc++.h>
using namespace std;

int n, m, ps, pt;

int main(){
	ios_base::sync_with_stdio(false);
	
	cin >> n >> m;
	vector<int> dp(m + 1, 0);
	
	for(int i = 0; i < n; i++){
		cin >> ps >> pt;
		for(int j = m; j >= pt; j--){
			dp[j] = max(dp[j], dp[j - pt] + ps);
		}
	}
	
	cout << dp[m];
}
```
