# Hướng dẫn Quản lý Nhiều App với Firebase trong Android

Tài liệu này hướng dẫn cách cấu hình Project để tạo ra nhiều phiên bản App khác nhau (ví dụ: bản Free, bản Pro, hoặc các môi trường Dev/Staging/Production) và cách kết nối chúng với Firebase mà không bị lỗi.

---

## 1. Tổng kết bài học từ lỗi vừa sửa
Lỗi `No matching client found` xảy ra khi:
- Bạn khai báo `applicationId` trong file `build.gradle.kts` (ví dụ: `com.easynote.app`).
- Nhưng trong file `google-services.json` tải về từ Firebase, bạn chưa đăng ký (register) package name đó, mà chỉ có package cũ (ví dụ: `com.example.take_note_app_1`).

**Quy tắc vàng:** `applicationId` trong code LUÔN LUÔN phải khớp với một trong các `package_name` trong file `google-services.json`.

---

## 2. Cách thiết lập để tạo nhiều App từ một Project

### Bước 1: Khai báo Product Flavors trong Gradle
Để tạo nhiều phiên bản App, bạn sử dụng `productFlavors` trong file `app/build.gradle.kts`.

```kotlin
android {
    ...
    flavorDimensions += "version"
    productFlavors {
        create("free") {
            dimension = "version"
            applicationId = "com.easynote.app.free"
            versionNameSuffix = "-free"
        }
        create("pro") {
            dimension = "version"
            applicationId = "com.easynote.app.pro"
            versionNameSuffix = "-pro"
        }
    }
}
```

### Bước 2: Đăng ký tất cả Package Name lên Firebase Console
1. Truy cập [Firebase Console](https://console.firebase.google.com/).
2. Chọn Project của bạn.
3. Nhấn vào **Add app** (biểu tượng Android) để thêm từng package name tương ứng:
   - Thêm app 1: `com.easynote.app.free`
   - Thêm app 2: `com.easynote.app.pro`
   - (Và bất kỳ ID nào khác bạn muốn dùng).

### Bước 3: Cập nhật file `google-services.json`
Sau khi thêm tất cả các App trên Firebase Console:
1. Tải lại file `google-services.json` mới nhất.
2. File mới này sẽ chứa mảng `client` bao gồm tất cả các package name bạn đã đăng ký.
3. Thay thế file cũ trong thư mục `app/` của bạn bằng file mới này.

### Bước 4: Kiểm tra và Build
- Khi bạn chọn Build Variant là `freeDebug`, Gradle sẽ dùng ID `com.easynote.app.free`.
- Firebase SDK sẽ tự tìm trong `google-services.json` xem có client nào khớp với ID đó không.
- Nếu khớp, App sẽ chạy bình thường.

---

## 3. Mẹo xử lý nhanh (Troubleshooting)

| Tình huống | Cách xử lý |
| :--- | :--- |
| **Muốn đổi tên App ID nhanh** | Đổi `applicationId` trong `defaultConfig` -> Lên Firebase đăng ký ID mới -> Tải lại `google-services.json`. |
| **Lỗi "No matching client"** | Mở file `google-services.json` ra, tìm từ khóa `"package_name"`. Copy chính xác giá trị đó dán vào `applicationId` trong Gradle. |
| **Nhiều file cấu hình khác nhau** | Bạn có thể đặt các file `google-services.json` riêng biệt vào các thư mục flavor: `src/free/google-services.json` và `src/pro/google-services.json`. |

---
*Chúc bạn phát triển ứng dụng thành công!*