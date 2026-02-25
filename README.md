
<p align="center">
  <img src="https://github.com/user-attachments/assets/d9b586c1-d5fc-48b6-b58f-2ee9c2304788" width="220" alt="Splash Screen" />
  <img src="https://github.com/user-attachments/assets/a314111a-0bfb-4329-96a6-3ef72b58eb77" width="220" alt="Main Screen" />
  <img src="https://github.com/user-attachments/assets/08ec7fd3-e681-4f9c-9172-a44040c7924c" width="220" alt="Main Scrolled" />
  <img src="https://github.com/user-attachments/assets/45b17cbb-4309-417e-bc74-051f0a14fa69" width="220" alt="Stories" />
</p>

<p align="center">
  <img src="https://github.com/user-attachments/assets/87da5f4d-d4b5-466b-bf28-1b7ebea504b9" width="220" alt="Yandex Map" />
  <img src="https://github.com/user-attachments/assets/2b986ee3-8ea0-4b0e-aff7-d2d618ac4509" width="220" alt="Branches" />
  <img src="https://github.com/user-attachments/assets/ca7ad9bb-0da6-49bc-95ae-ffcbe27e06b4" width="220" alt="Cart BottomSheet" />
  <img src="https://github.com/user-attachments/assets/f5b4e1d5-e267-4b86-afef-e68d003d345b" width="220" alt="Product Details" />
</p>

<p align="center">
  <img src="https://github.com/user-attachments/assets/cc0e2683-b32d-4cc0-9378-ba2da2538d81" width="220" alt="Orders History" />
  <img src="https://github.com/user-attachments/assets/8f4cebcc-5a29-4391-87f3-5eee91768cfb" width="220" alt="Notifications" />
</p>


# 🍔 MaxWay App - Fast Food Delivery Clone

<p align="center">
  <img src="https://img.shields.io/badge/Platform-Android-brightgreen?style=for-the-badge&logo=android" />
  <img src="https://img.shields.io/badge/Language-Kotlin-orange?style=for-the-badge&logo=kotlin" />
  <img src="https://img.shields.io/badge/Architecture-MVVM-blue?style=for-the-badge&logo=androidstudio" />
</p>

---

## 📝 Loyiha haqida (About the Project)

**MaxWay App** — bu zamonaviy fast-food tarmog'ining to'liq funksional Android kloni. Loyiha davomida foydalanuvchi tajribasini (UX) maksimal darajada qulay qilish, ma'lumotlarni tezkor yuklash va reaktiv dasturlash prinsiplariga amal qilingan. 

Ilova **REST API** bilan ishlaydi va foydalanuvchilarga taomlarni tanlash, kategoriyalar bo'yicha qidirish va manzilni xaritadan belgilash imkonini beradi.

---

## 🛠 Texnik Imkoniyatlar (Technical Stack)

Loyihaning asosi eng so'nggi Android standartlariga tayanadi:

### 🏛 Arxitektura va Patternlar
* **MVVM (Model-View-ViewModel):** Kodning tozaligi va testga qulayligini ta'minlash uchun.
* **Manual Dependency Injection:** Obyektlar iyerarxiyasini chuqur tushunish va nazorat qilish uchun (Hilt-siz amalga oshirilgan).
* **ViewBinding Property Delegation:** Fragmentlarda `onDestroyView` vaqtida xotira (memory leak) muammolarini avtomatik hal qiluvchi delegatdan foydalanilgan.

### ⚡ Reaktiv Dasturlash (Reactive Streams)
* **Kotlin Coroutines & Flow:** Asinxron vazifalar va ma'lumotlar oqimi uchun.
* **StateFlow:** UI holatini (State) barqaror saqlash uchun.
* **SharedFlow:** Navigatsiya va xabarlar (Snackbars) kabi bir martalik hodisalarni (Events) boshqarish uchun.
* **LiveData:** Hayotiy sikl (Lifecycle) bilan integratsiya qilingan ma'lumotlar uzatish.

### 🌐 Tarmoq va Ma'lumotlar
* **Retrofit & OkHttp:** API so'rovlarini xavfsiz va tezkor amalga oshirish.
* **Glide:** Rasmlarni aqlli keshlashtirish va yuklash.
* **Shared Preferences:** Foydalanuvchi profili va sessiyasini lokal saqlash.

---

## 🔥 Asosiy Funksiyalar (Key Features)

| Funksiya | Tavsif |
| :--- | :--- |
| **Auth System** | Foydalanuvchilarni ro'yxatdan o'tkazish va Login tizimi. |
| **Yandex Maps** | Manzilni xarita orqali aniq ko'rsatish va yetkazib berish nuqtasini tanlash. |
| **Search & Filter** | Taomlarni nomi bo'yicha qidirish va kategoriyalar (Burger, Lavash, Ichimliklar) bo'yicha filtrlash. |
| **Jetpack Navigation** | Fragmentlar orasidagi murakkab o'tishlar va SafeArgs orqali ma'lumot uzatish. |
| **Dynamic UI** | Ma'lumotlar yuklanayotgan vaqtda Shimmer effect yoki Progress bar holatlari. |

---

## 🗺️ Kelajakdagi Rejalar (Roadmap)

Loyihani yanada rivojlantirish uchun quyidagi funksiyalarni qo'shish rejalashtirilgan:
- [ ] 🌙 **Dark Mode:** Tungi rejimni to'liq qo'llab-quvvatlash.
- [ ] 💳 **Payment Integration:** Click yoki Payme orqali onlayn to'lov tizimi.
- [ ] 🔔 **Push Notifications:** Buyurtma holati haqida real-vaqtda bildirishnomalar.
- [ ] 🧪 **Unit & UI Testing:** Kod sifatini ta'minlash uchun testlar yozish.
- [ ] 📦 **Order Tracking:** Buyurtma yetkazilishini xaritada jonli kuzatish.

---
## 🚀 O'rnatish va Ishga tushirish (Installation)

Loyihani o'z kompyuteringizda ishga tushirish uchun quyidagi qadamlarni bajaring:

1. **Repozitoriyadan nusxa oling:**
   ```bash
   git clone [https://github.com/uzbedeveloper/MaxWay-APP.git](https://github.com/uzbedeveloper/MaxWay-APP.git)
---
##

## 🛠️ Qanday qilib hissa qo'shish mumkin? (Contribution)

Loyiha ochiq manbali (Open Source) hisoblanadi. Agar sizda takliflar bo'lsa:
1. Loyihani **Fork** qiling.
2. Yangi **Branch** yarating (`git checkout -b feature/AmazingFeature`).
3. O'zgarishlarni **Commit** qiling (`git commit -m 'Add some AmazingFeature'`).
4. Branchga **Push** qiling (`git push origin feature/AmazingFeature`).
5. **Pull Request** yuboring.

------

## 🤝 Mualliflar (Authors)

Loyiha bo'yicha savollar yoki takliflar bo'lsa, biz bilan bog'lanishingiz mumkin:

<table align="center">
  <tr>
    <td align="center">
      <a href="https://github.com/uzbedeveloper">
        <img src="https://github.com/uzbedeveloper.png" width="100px;" alt="UzbeDeveloper"/><br />
        <sub><b>UzbeDeveloper</b></sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/Kenesov">
        <img src="https://github.com/Kenesov.png" width="100px;" alt="Kenesov"/><br />
        <sub><b>Kenesov</b></sub>
      </a>
    </td>
  </tr>
</table>

<p align="center">
  <a href="https://github.com/uzbedeveloper">
    <img src="https://img.shields.io/badge/Follow-UzbeDeveloper-black?style=for-the-badge&logo=github" />
  </a>
  <a href="https://github.com/Kenesov">
    <img src="https://img.shields.io/badge/Follow-Kenesov-black?style=for-the-badge&logo=github" />
  </a>
</p>

<p align="center">
  <i>Created by <a href="https://github.com/uzbedeveloper">UzbeDeveloper</a> & <a href="https://github.com/Kenesov">Kenesov</a></i>
</p>
