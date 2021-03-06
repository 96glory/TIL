# 정렬
## 1. 선택 정렬
- ![selectionsort](https://hudi.kr/wp-content/uploads/2018/02/selectionsort.gif)
```C++
#include <iostream>

using namespace std;

int arr[101];

int main(){
	int n, min = 987654321, minIndex = 0, temp;
	scanf("%d", &n);
	
	for(int i = 1; i <= n; i++)
		scanf("%d", &arr[i]);
	
	for(int i = 1; i < n; i++){
		for(int j = i; j <= n; j++){
			if(min >= arr[j]){
				min = arr[j];
				minIndex = j;
			}
		}
		
		temp = arr[i];
		arr[i] = min;
		arr[minIndex] = temp;
		
		min = 987654321;
	}
	
	for(int i = 1; i <= n; i++){
		printf("%d ", arr[i]);
	}
}
```

## 2. 버블 정렬
- ![bubblesort](https://upload.wikimedia.org/wikipedia/commons/0/06/Bubble-sort.gif)
```C++
#include <iostream>

using namespace std;

int arr[101];

int main(){
	int n, temp;
	scanf("%d", &n);
	
	for(int i = 1; i <= n; i++)
		scanf("%d", &arr[i]);

	for(int i = 1; i < n; i++){
		for(int j = 1; j < n - i; j++){
			if(arr[j] > arr[j + 1]){
				temp = arr[j];
				arr[j] = arr[j + 1];
				arr[j] = temp;
			}
		}
	}
	
	for(int i = 1; i <= n; i++)
		printf("%d ", arr[i]);
}
```

## 3. 삽입 정렬
- ![insertionsort](https://upload.wikimedia.org/wikipedia/commons/0/0f/Insertion-sort-example-300px.gif)
```C++
#include <iostream>

using namespace std;

int arr[101];

int main(){
	int n, temp, i, j;
	scanf("%d", &n);
	
	for(i = 1; i <= n; i++)
		scanf("%d", &arr[i]);
	
	for(i = 2; i <= n; i++){
		temp = arr[i];
		for(j = i - 1; j >= 1; j--){
			if(arr[j] > temp)	arr[j + 1] = arr[j];
			else				break;
		}
		arr[j + 1] = temp;
	}
	
	for(i = 1; i <= n; i++)
		printf("%d ", arr[i]);
}
```

## 4. 병합 정렬
- ![mergesort](https://upload.wikimedia.org/wikipedia/commons/c/cc/Merge-sort-example-300px.gif)
	- 큰 배열을 작은 배열로 divide, 하위 배열들을 합치면서 정렬.
```cpp
#include <iostream>
using namespace std;

int data[10], tmp[10];

void divide(int left, int right){
	int mid, p1, p2, p3;
	if(left < right){
		mid = (left + right) / 2;
		
		// 분할. 
		divide(left, mid);
		divide(mid + 1, right);
		
		// 정렬 후 병합.
		p1 = left;
		p2 = mid + 1;
		p3 = left;
		while(p1 <= mid && p2 <= right){
			if(data[p1] < data[p2])
				tmp[p3++] = data[p1];
			else
				tmp[p3++] = data[p2];
		}
		while(p1 <= mid)	tmp[p3++] = data[p1++];
		while(p2 <= right)	tmp[p3++] = data[p2++];
		for(int i = left; i <= right; i++)
			data[i] = tmp[i];
	}
}

int main(){
	int n, i;
	scanf("%d", &n);
	for(i = 1; i <= n; i++)	scanf("%d", &data[i]);
	divide(1, n);
	for(i = 1; i <= n; i++)	printf("%d ", data[i]);
}
```



## C++ STL Algorithm의 sort함수 이용하기.
* 라이브러리 추가하기
```cpp
#include <algorithm>
```

### 배열인 경우

#### 오름차순
```cpp
int arr[10] = {5, 4, 3, 2, 1, 9, 8, 7, 6, 10};
sort(arr, arr + 10);
```

#### 내림차순
```cpp
int arr[10] = {5, 4, 3, 2, 1, 9, 8, 7, 6, 10};
sort(arr, arr + 10, greater<int>());
```

### vector인 경우
#### 오름차순
```cpp
sort(v.begin(), v.end());
```
#### 내림차순
```cpp
sort(v.rbegin(), v.rend());
```
