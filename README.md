# Paddap
AndroidをMacのトラックパッド代わりに使うアプリ
Androidアプリがサーバーとして動作しタッチパネルのイベントをMac(クライアント)に送信する
Mac(クライアント)はそれを受信しマウスを動かす等の動作をする

週末ものづくり体験講座
http://weekend-fabrication.yasulab.com/
の授業の1週目で開発したもの

## どこまで出来てるか
USBで接続してマウスポインタを移動クリック出来るところまで

### Android側:
* タッチのイベントを拾ってUSB経由やWiFi経由でPCにイベントを送るまで

### Mac側
* Androidアプリ側から送られてきたタッチの動作を取得しマウスポインタを移動・クリックさせる
* MacRubyで開発しているので実行にはMacRubyが必要

## 使い方
AndroidとMacを接続する必要がある

### ネットワーク経由で接続する場合
paddap.rbのhostをAndroidに設定されているのIPアドレスで書き換える

### USB経由で接続する場合
Android端末をUSBでつなぎ
`$ adb forward tcp:6666 tcp:6666`
を実行しAndroidの通信をlocalhostのポート6666番で待ち受けるようにする
paddap.rbのhostをlocalhostに設定する

### Android側
Paddapを立ち上げる

### Mac側
MacRubyをインストールする
http://www.macruby.org/downloads.html
Androidでアプリを立ち上げてからpaddap.rbを実行する
`$ macruby paddap.rb`

### 実装されている操作
タッチパネルに触れたまま移動でマウスポインタの移動、タップでクリック
