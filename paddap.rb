# -*- coding: utf-8 -*-
require "socket"
framework 'Cocoa'

# イベントを作成/投げる時に使う定数を宣言
kCGMouseButtonLeft = 0
kCGEventMouseMoved = 5
kCGEventLeftMouseDown = 1
kCGEventLeftMouseUp = 2
kCGHIDEventTap = 0

host = "localhost" # 接続先ホスト
port = 6666 # ポート番号

# 接続を開く
s = TCPSocket.open(host, port)

# マウスの初期位置
point = NSEvent.mouseLocation
begin
  while l = s.gets do
    # クリックされた時
    if l['click'] then
      puts "clicked"
      # マウス押下のイベントを投げる
      ev = CGEventCreateMouseEvent(nil, kCGEventLeftMouseDown, point, kCGMouseButtonLeft)
      CGEventPost(kCGHIDEventTap, ev)
      CFRelease(ev)

      # マウス開放のイベントを投げる
      ev = CGEventCreateMouseEvent(nil, kCGEventLeftMouseUp, point, kCGMouseButtonLeft)
      CGEventPost(kCGHIDEventTap, ev)
      CFRelease(ev)

    # マウスを動かした時
    else
      # 移動距離を取得
      x, y = l.split(',').map(&:to_f)
      
      # 移動後の縦横の位置
      new_x = x + point.x
      new_y = y + point.y
      
      # FIXME: 新しい位置がはみ出さないようにする処理
      # new_x = 0 if new_x < 0
      # new_y = 0 if new_y < 0
      # new_x = display_size.width if display_size.width < new_x
      # new_y = display_size.height if display_size.height < new_y
      # display_size = CGDisplayScreenSize(CGMainDisplayID())
      # puts "height:#{display_size.height}, width:#{display_size.width}"
      # bounds = CGDisplayBounds(CGMainDisplayID())

      # 新しい位置へのポイント
      point = CGPoint.new(new_x, new_y)
      
      # マウスを移動するイベントを投げる
      puts "move x:#{point.x}, y:#{point.y}, to new_x:#{new_x}, new_y:#{new_y}"
      ev = CGEventCreateMouseEvent(nil, kCGEventMouseMoved, point, kCGMouseButtonLeft)
      CGEventPost(kCGHIDEventTap, ev)
      CFRelease(ev)
    end
  end
ensure
  # 終了する時に閉じる
  puts "close"
  s.close
end
