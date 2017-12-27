import argparse
import logging
import tensorflow as tf
from tensorflow.python.framework.graph_util import convert_variables_to_constants


class Trainer(object):
    def __init__(self, args):
        self.args = args

    def run_training(self):
        input = [[0, 0], [0, 1], [1, 0], [1, 1]]
        output = [[0], [0], [0], [1]]

        x = tf.placeholder(tf.float32, shape=[None, 2], name='x')
        y = tf.placeholder(tf.float32, shape=[None, 1], name='y')

        weight = tf.Variable(tf.zeros([2, 1]), dtype=tf.float32, name='weight')
        bias = tf.Variable(tf.zeros([1]), name='bias')

        y_pred = tf.nn.sigmoid(tf.matmul(x, weight) + bias, name='y_pred')

        with tf.name_scope("loss"):
            loss = tf.reduce_sum(tf.square(y_pred - y), name='loss')

        with tf.name_scope("train"):
            optimizer = tf.train.AdamOptimizer(learning_rate=0.1, name='optimizer')
            train_step = optimizer.minimize(loss, name='train_step')

        tf.summary.histogram("weight", weight)
        tf.summary.histogram("bias", bias)
        tf.summary.scalar('loss', loss)
        merged = tf.summary.merge_all()

        with tf.Session() as session:
            session.run(tf.global_variables_initializer())
            writer = tf.summary.FileWriter(self.args.output_path + 'logs/raw', session.graph)

            for epoch in range(self.args.epochs):
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
            # tf.train.write_graph(graph_def, 'models', 'and.pb', as_text=False)
            # tf.train.write_graph(graph_def, 'models', 'and.pb.txt', as_text=True)

            if self.args.output_format == 'pb':
                as_text = False
            else:
                as_text = True
            graph_def = convert_variables_to_constants(session, session.graph_def, ['y_pred'])
            tf.train.write_graph(graph_def, self.args.output_path + 'models', 'logic_and_gate.pb', as_text=as_text)


def main(_):
    parser = argparse.ArgumentParser()
    parser.add_argument(
        '--epochs',
        type=int,
        default=1001
    )
    parser.add_argument(
        '--output_path',
        type=str,
        default='../'
    )
    parser.add_argument(
        '--output_format',
        type=str,
        default='pb'
    )
    args, _ = parser.parse_known_args()
    Trainer(args).run_training()


if __name__ == '__main__':
    logging.basicConfig(level=logging.INFO)
    tf.app.run()
