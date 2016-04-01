#!/bin/bash

host_name="localhost"
port="8080"

target_url="http://google.com"
create_result=`mktemp`

json_data="{\"url\":\"$target_url\"}"

curl -sG --data-urlencode "url=$target_url" "http://$host_name:$port/admin/create" > "$create_result"

abbr_url=`head -n1 "$create_result"`

rm "$create_result"

if curl -sIv "$abbr_url" 2>&1 |grep -q "$target_url"; then
    echo "pass"
    exit 0
else
    echo "fail"
    exit 1
fi

