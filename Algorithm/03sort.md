# 정렬
## 1. 선택 정렬
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
