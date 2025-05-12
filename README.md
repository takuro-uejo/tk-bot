## tk-bot

### 概要
takuroの分身と会話ができるLINE-Botの、Java（Spring）で遊ぶ用

（慣れてきたらそのうち）Ruby（Ruby on Rails）で遊ぶ用も作る

### デバッグ方法（ローカル）

- 1. アプリのビルドを作る
```
docker build -t tk-bot .
```

- 2. 任意のローカルホストを指定してDockerコンテナを動かす（例：8082使っている場合）
```
docker run -p 8082:8082 tk-bot
```

- 3. ブラウザで動かす（例：8082使っている場合）
```
http://localhost:8082 にアクセス
```