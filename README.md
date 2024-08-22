# CoindeskHelper 專案

## 概述

這是一個 Spring Boot 應用程式，透過 Quartz 定期從外部 API 取回資料並儲存到資料庫。應用程式提供 RESTful API 進行資料庫中的查詢、修改、刪除（CRUD）操作。此外，專案使用 AOP（面向切面編程）將每個操作的日誌記錄存入資料庫，並支援多語系的資料管理功能。專案也整合了 Swagger，用於測試和探索 API。

## 目錄

- [專案功能](#專案功能)
- [使用技術](#使用技術)
- [安裝](#安裝)
- [配置](#配置)
- [使用方法](#使用方法)
- [API 端點](#api-端點)
- [日誌記錄](#日誌記錄)
- [Swagger 文件](#swagger-文件)
- [UT Coverage](#ut-coverage)

## 專案功能

- **Quartz 排程**：定期從外部 API 取回資料並儲存到資料庫。
- **CRUD 操作**：提供 RESTful API 進行資料的查詢、刪除操作。
- **多語系支持**：支援多語系的資料管理功能，包含新增、修改等操作。
- **AOP 日誌記錄**：自動記錄每次 API 操作的日誌並存入資料庫。
- **Swagger 集成**：透過 Swagger 提供 API 文件和測試功能。

## 使用技術

- Spring Boot
- Quartz
- AOP
- H2 資料庫
- RESTful API
- Swagger

## 安裝

1. ** clone 此專案**：
    ```bash
    git clone https://github.com/yourusername/your-repo-name.git
    cd your-repo-name
    ```

2. **編譯專案**：
    ```bash
    ./mvnw clean install
    ```

3. **運行應用程式**：
    ```bash
    ./mvnw spring-boot:run
    ```

## 配置

- 你可以在 `application.properties` & `coindeskhelper.properties` 文件中自訂所需參數

- `src/main/resources/coindeskhelper.properties` 中的範例配置：
    ```properties
	# CALL 外部 API TIMEOUT 設定 (以秒為單位)
	props.api.timeout.connection=5
	props.api.timeout.read=5
	props.api.timeout.write=5

	# 外部 API URL
	props.api.coindesk.baseuri=https://api.coindesk.com
	props.api.coindesk.currentprice=/v1/bpi/currentprice.json

    # Quartz 頻率 (以秒為單位)
	props.schedule.interval=600
    ```

## 使用方法

- 當應用程式運行後，Quartz 將根據配置的頻率自動啟動任務，從外部 API 取回資料並儲存到資料庫。

- 應用程式提供多個 RESTful API 端點，用於與資料進行互動。

## API 端點

- **根據語系取得資料**：
    ```
    GET /api/bitcoin/en
    ```

- **新增或更新語系相關資料**：
    ```
    POST /api/bitcoin/translation/add-or-update
    Body: {
        "code": "USD",
        "language": "EN",
        "name": "USD",
        "description": "description"
    }
    ```

- **刪除一筆語系記錄**：
    ```
    DELETE /api/bitcoin/translation/{code}/{language}
    ```

- **刪除一筆記錄**：
    ```
    DELETE /api/bitcoin/{code}
    ```

## 日誌記錄

- 本專案使用 AOP 自動記錄所有 CRUD 操作，並將日誌存儲在資料庫中的 `ExecutionLog` 表中。
- 日誌記錄的內容包含類名、方法名、參數、返回值，以及方法執行的時間和持續時間。

## Swagger 文件

- 啟動應用程式後，你可以透過瀏覽器訪問以下 URL 來查看 Swagger 生成的 API 文件：
    ```
    http://localhost:8123/swagger-ui/index.html
    ```
- 透過 Swagger UI，可以方便地測試和探索 API。

## UT Coverage

- 使用 jacoco 自動產生
    ```bash
    mvn test
    ```
- 檔案路徑: target/site/index.html