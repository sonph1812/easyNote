# 📝 Hướng dẫn Chạy & Phát triển Take Note App

Tài liệu này giúp bạn setup nhanh dự án sau khi clone hoặc chuyển sang máy tính mới.

## 1. Yêu cầu hệ thống
* **Android Studio:** Bản mới nhất (Ladybug hoặc Koala).
* **JDK:** 17 hoặc 21 (Đi kèm sẵn trong Android Studio).
* **Bộ nhớ:** Trống ít nhất 10GB ổ C (cho SDK và Gradle).

## 2. Setup dự án nhanh
1. **Clone project** về máy.
2. **Mở Android Studio**, chọn *Open* và trỏ đến thư mục dự án.
3. **Gradle Sync:** Đợi Android Studio tải thư viện. Nếu bị lỗi `JAVA_HOME`, hãy chạy lệnh này trong Terminal:
   ```powershell
   $env:JAVA_HOME = "C:\Program Files\Android\Android Studio\jbr"
   ./gradlew tasks
   ```

## 3. Các lỗi đã fix (Quan trọng)
* **Lỗi KSP (JVM Signature V):** Đã nâng cấp Room lên `2.8.4` để tương thích với Kotlin 2.x. Không nên hạ cấp Room xuống 2.6.x.
* **Lỗi Icons:** Đã thêm thư viện `material-icons-extended` để fix lỗi thiếu icon trên giao diện.

## 4. Kiểm thử (Testing)
### Chạy trên máy tính (Unit Test - Cực nhanh)
Không cần điện thoại, dùng để check logic Repository/ViewModel:
* **Giao diện:** Mở file trong `app/src/test`, nhấn nút **Play xanh** cạnh tên Class.
* **Terminal:** `./gradlew :app:testDebugUnitTest`

### Chạy trên điện thoại (Instrumented Test)
Dùng để check Database Room thật:
* **Terminal:** `./gradlew :app:connectedDebugAndroidTest`

## 5. Chạy App trên điện thoại thật (Xiaomi/Redmi)
Để tránh lỗi `Installation failed`, cần bật các mục sau trong **Developer Options**:
1. **USB Debugging:** ON.
2. **Install via USB:** ON (Bắt buộc).
3. **Chế độ kết nối USB:** Luôn chọn **File Transfer** (Truyền tệp).

## 6. Cấu trúc dự án
* `data/`: Database (Room), DAO, Repository.
* `ui/`: Các màn hình Compose (NoteList, AddEdit).
* `gradle/libs.versions.toml`: Quản lý phiên bản thư viện.

---
*Chúc bạn phát triển ứng dụng vui vẻ!* 🚀
