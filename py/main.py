import sys
import requests
from concurrent.futures import ProcessPoolExecutor


def send_request(url):
    print(url)
    response = requests.get(url)
    print("[{status}] {url}".format(status=response.status_code, url=url))
    return response.status_code


if __name__ == '__main__':
    arg_params = sys.argv
    arg_params.pop(0)
    if len(arg_params) < 2:
        raise "Usage: python main.py <url1> <url2> .. <urln>"

    url_list = ["https://"+url for url in arg_params]
    with ProcessPoolExecutor() as executor:
        executor.map(send_request, url_list)

