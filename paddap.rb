require "socket"
framework 'Cocoa'

port = 6666

s = TCPSocket.open("localhost", port)
point = NSEvent.mouseLocation
begin
  while l = s.gets do
    if l['click'] then
      puts "clicked!"
      raise
    else
      x, y = l.split(',').map(&:to_i)
      new_x = x + point.x.to_i
      new_y = y + point.y.to_i
      point = CGPoint.new(new_x, new_y)
      
      # puts "move x:#{x}, y:#{y}, to new_x:#{new_x}, new_y:#{new_y}"
      CGWarpMouseCursorPosition point
    end
  end
ensure
  puts "close"
  s.close
end
