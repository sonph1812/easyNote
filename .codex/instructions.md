# Agent Instructions: Easy Note App Development & Maintenance

Tài liệu này hướng dẫn một AI Agent cách hiểu, xây dựng lại hoặc duy trì dự án **Easy Note**.

## 1. Tổng quan dự án (Project Context)
- **Tên App:** Easy Note
- **Mục tiêu:** Ứng dụng ghi chú đơn giản, hiện đại, hiệu suất cao.
- **Ngôn ngữ:** Kotlin
- **UI Framework:** Jetpack Compose
- **Kiến trúc:** MVVM (Model-View-ViewModel) kết hợp với Clean Architecture cơ bản.

## 2. Tech Stack & Dependencies
Agent cần chú ý các thành phần cốt lõi:
- **Database:** Room Persistence Library (để lưu trữ offline).
- **Dependency Injection:** (Kiểm tra project để xem dùng Hilt, Koin hay manual provider).
- **Backend/Services:** Firebase (Analytics, Google Services).
- **Build System:** Gradle Kotlin DSL (`.gradle.kts`).

## 3. Quy tắc cấu hình quan trọng (Critical Configurations)
Khi làm việc với dự án này, Agent PHẢI tuân thủ:
- **Application ID:** Phải luôn khớp với file `google-services.json`. Hiện tại là `com.example.take_note_app_1`.
- **Git Ignore:** Không đẩy các file `.md` tài liệu cá nhân lên, trừ `README.md`.
- **Branding:** App sử dụng tông màu chủ đạo **Vàng - Trắng (Gold & White)** cho Icon và định hướng giao diện sang trọng.

## 4. Cấu trúc thư mục (Folder Structure)
- `data/`: Chứa Entity (Note), DAO, Database và Repository.
- `ui/`: Chứa các Composable functions, ViewModel và Theme (Color.kt, Type.kt).
- `res/`: Quản lý tài nguyên, đặc biệt là `drawable/ic_launcher_*` cho Adaptive Icon.

## 5. Hướng dẫn phát triển tính năng mới
Khi Agent được yêu cầu thêm tính năng:
1. **Model:** Cập nhật `Note.kt` nếu cần thêm trường dữ liệu.
2. **Data Layer:** Thêm function vào `NoteDao.kt` và `NoteRepository.kt`.
3. **Logic:** Xử lý trong ViewModel tương ứng.
4. **UI:** Xây dựng màn hình bằng Jetpack Compose, đảm bảo tuân thủ Theme Gold/White.

## 6. Checklist trước khi Commit/Push
- [ ] Kiểm tra `applicationId` trong `build.gradle.kts`.
- [ ] Chạy Gradle Sync để đảm bảo không có lỗi dependency.
- [ ] Kiểm tra icon hiển thị đúng trên các loại màn hình (Adaptive Icon).
- [ ] Đảm bảo không có thông tin nhạy cảm (API Keys) bị lộ.

---
*Tài liệu này giúp Agent tiếp quản dự án nhanh chóng và duy trì tính nhất quán.*