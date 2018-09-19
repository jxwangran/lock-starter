for i,v in ipairs(KEYS) do
	if redis.call('setnx', v, ARGV[1]) == 1 
		then redis.call('pexpire', v, ARGV[2]) 
	else 
		return v
	end
end

return 1