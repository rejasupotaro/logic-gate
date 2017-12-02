import tensorflow as tf
from tensorflow.python.framework.graph_util import convert_variables_to_constants
from tensorflow.core.framework import graph_pb2
from tensorflow.python.framework import dtypes
from tensorflow.python.platform import gfile
from tensorflow.python.tools import optimize_for_inference_lib


def import_graph():
    with tf.Graph().as_default():
        # Load
        with tf.gfile.FastGFile('models/optimized_and.pb', 'rb') as file:
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
            y_pred = session.run('y_pred:0', feed_dict={
                'x:0': [[1, 1]]
            })
            print('y_pred: ', y_pred)


def optimize_graph():
    with gfile.Open('models/and.pb', "rb") as file:
        input_graph_def = graph_pb2.GraphDef()
        input_graph_def.ParseFromString(file.read())

        output_graph_def = optimize_for_inference_lib.optimize_for_inference(
            input_graph_def,
            input_node_names=['x'],
            output_node_names=['y_pred'],
            placeholder_type_enum=dtypes.float32.as_datatype_enum
        )

        file = gfile.FastGFile('models/optimized_and.pb', "w")
        file.write(output_graph_def.SerializeToString())


def save_graph_def(graph_def):
    tf.train.write_graph(graph_def, 'models', 'and.pb', as_text=False)
    tf.train.write_graph(graph_def, 'models', 'and.pb.txt', as_text=True)


def train():
    input = [[0, 0], [0, 1], [1, 0], [1, 1]]
    output = [[0], [0], [0], [1]]

    x = tf.placeholder(tf.float32, shape=[None, 2], name='x')
    y = tf.placeholder(tf.float32, shape=[None, 1], name='y')

    weight = tf.Variable(tf.zeros([2, 1]), dtype=tf.float32, name='weight')
    bias = tf.Variable(tf.zeros([1]), name='bias')
    y_pred = tf.nn.sigmoid(tf.matmul(x, weight) + bias, name='y_pred')

    with tf.name_scope("loss"):
        loss = -tf.reduce_sum(y * tf.log(y_pred) + (1 - y) * tf.log(1 - y_pred), name='loss')

    with tf.name_scope("train"):
        optimizer = tf.train.AdamOptimizer(learning_rate=0.1, name='optimizer')
        train_step = optimizer.minimize(loss, name='train_step')

    tf.summary.histogram("weight", weight)
    tf.summary.histogram("bias", bias)
    tf.summary.scalar('loss', loss)
    merged = tf.summary.merge_all()

    with tf.Session() as session:
        session.run(tf.global_variables_initializer())
        writer = tf.summary.FileWriter('logs/raw', session.graph)

        for epoch in range(1001):
            _, summary, l = session.run(
                [train_step, merged, loss],
                feed_dict={
                    x: input, y: output
                }
            )

            if epoch % 100 == 0:
                print("epoch: {}, loss: {}".format(epoch, l))
                writer.add_summary(summary, epoch)

        for data in input:
            print("input: {} | output: {}".format(data, session.run(y_pred, feed_dict={x: [data]})))

        # If you want to save checkpoint
        # tf.train.Saver().save(session, "models/and.ckpt")

        # If you don't want to save variables
        # graph_def = session.graph_def
        # If you want to save (= freeze) variables as constants
        graph_def = convert_variables_to_constants(session, session.graph_def, ['y_pred'])

        save_graph_def(graph_def)


if __name__ == '__main__':
    train()
    optimize_graph()
    import_graph()
