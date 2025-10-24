# Blog System

Java 21 / Spring Boot 製の簡単なブログシステムです。

## 主な機能
- 公開記事の閲覧（一覧、詳細）
- 管理者用ダッシュボード
  - 記事の作成・編集・削除
  - ユーザーアカウントの管理（作成・編集・削除・有効/無効）

## 初期アカウント
- ユーザー名: `admin`
- パスワード: `password`

## 動作方法
```bash
mvn spring-boot:run
```

## 開発用メモ
- データベース: H2 (インメモリ)
- H2 コンソール: `http://localhost:8080/h2-console`
