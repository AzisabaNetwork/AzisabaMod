# AzisabaMod

All-in-one mod for Azisaba

## メイン機能

- 画像URLのゲーム内表示
- Build-Toolのプレビュー表示
- 1.21.9～1.21.11の[暗視エフェクトの変更 (MC-301543)](https://bugs.mojang.com/browse/MC/issues/MC-301543)をロールバック (26.1+の挙動と一致)
- WebSocket経由でのMinecraftサーバーへの接続 (サーバーアドレス欄の`ws://`と`wss://`のサポート)

## デバッグ用機能

- デバッグ用オーバーレイ (F3+F6)
  - Build-Toolモード
  - 現在の画面(クラス名)
  - Ping
  - TPS
  - プレイヤーリスト(Tab)の権限表示
- アイテム説明 (F3+F6)
  - `custom_model_data`
  - `item_model`
  - `repair_cost`
  - CSID (CrackShot Item ID)
  - MMID (MythicMobs Item ID)
  - Soulbound
  - ShulkerId
  - アイテム詳細(Attribute Modifiers、エンチャントなど)の強制表示/デフォルト設定
- `/azisaba`内の様々なコマンド

## Fabric

### 1.21.11

#### 前提Mod

- [fabric-api](https://modrinth.com/mod/fabric-api)
- [Cloth Config API](https://modrinth.com/mod/cloth-config)

#### あったほうがいいMod

- [Mod Menu](https://modrinth.com/mod/modmenu)

### 手順

1. Mod設定にAPIキーを入れる(APIキーはアジ鯖内で`/apikey`をすることで入手可能)
