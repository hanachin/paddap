# Paddap
Paddap is an Android app for remote control of your Mac.

You can control Mac from Android.

This app is made in weekend fabricatoin class.

http://weekend-fabrication.yasulab.com/

## Demo movie
Paddap demo

http://www.youtube.com/watch?v=OBkrbA452JE

## Usage
Connect android to Mac via USB or LAN.

### Connecting via LAN
Edit the paddap.rb to set the host variable to Android's IP address.

### Connecting via USB
Connect your Android to Mac and open Terminal.app and run this command.

`$ adb forward tcp:6666 tcp:6666`

### Android operation
Run Paddap.

### Mac operation
Paddap requires MacRuby.

http://www.macruby.org/downloads.html

Execute paddap.rb
`$ macruby paddap.rb`

# Paddap
AndroidをMacのトラックパッド代わりに使うアプリ。

Androidアプリがサーバーとして動作しタッチパネルのイベントをMac(クライアント)に送信する。

Mac(クライアント)はイベントを受信しマウスを動かす等の動作をする。

週末ものづくり体験講座の授業の1週目で開発したもの。

http://weekend-fabrication.yasulab.com/


## デモ動画
Paddap demo

http://www.youtube.com/watch?v=OBkrbA452JE

## 実装済み
### Android側
* タッチのイベントを拾ってUSB経由・ネットワーク経由でPCにイベントを送るまで。

### Mac側
* Androidアプリ側から送られてきたタッチの動作を取得しマウスポインタを移動・クリックさせる。
* MacRubyで開発しているので実行にはMacRubyが必要。

## 未実装
* スクロール
* 右クリック

## 使い方
AndroidとMacをUSBまたはLAN経由で接続する必要がある。

### ネットワーク経由で接続する場合
paddap.rbのhostをAndroidに設定されているのIPアドレスで書き換える。

### USB経由で接続する場合
Android端末をUSBでつなぎ次のコマンドをを実行し、Androidの通信をlocalhostのポート6666番で待ち受けるようにする

`$ adb forward tcp:6666 tcp:6666`

paddap.rbのhostをlocalhostに設定する。

### Android側
Paddapを立ち上げる。

### Mac側
MacRubyをインストールする。

http://www.macruby.org/downloads.html

Androidでアプリを立ち上げてからpaddap.rbを実行する。

`$ macruby paddap.rb`
