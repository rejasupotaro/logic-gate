import tensorflow as tf

if __name__ == '__main__':
    with tf.Graph().as_default():
        # Load
        with tf.gfile.FastGFile('models/xor.pb', 'rb') as file:
            graph_def = tf.GraphDef()
            graph_def.ParseFromString(file.read())
            tf.import_graph_def(graph_def, name='')

        # Analyze
        for op in tf.get_default_graph().get_operations():
            print(op.name)
            for output in op.outputs:
                print('  ', output.name)
        print('=' * 60)

        # Run
        with tf.Session() as session:
            loss = session.run('loss:0', feed_dict={'input:0': [[1, 1]], 'output:0': [1]})
            print('result: {0}'.format(loss))
